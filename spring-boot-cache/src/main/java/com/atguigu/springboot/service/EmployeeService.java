package com.atguigu.springboot.service;

import com.atguigu.springboot.bean.Employee;
import com.atguigu.springboot.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * @description：
 * @author： wangkang
 * @date： 2019/7/13 21:51
 */
@Service
//@CacheConfig(cacheNames="emp") //抽取缓存的公共配置
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;
    /**
     * 将方法的运行结果进行缓存，以后再要相同的数据，直接从缓存中获取，不用调用方法
     * CacheManager管理多个Cache组件的，对缓存的真正crud操作在cache组件中，每一个缓存组件有自己唯一的一个名字
     * 属性：
     *      cacheNames/value:指定缓存组件的名字
     *      key:缓存数据使用的key;可以用它来指定。默认是使用方法的参数的值，id的值：方法返回的值
     *          编写SpEL：#id,参数的id的值 #a0 #p0 #root.args[0]
     *      keyGenerator:key的生成器；可以自己指定key的生成器的组件id
     *          key/keyGenerator:二选一使用
     *      cacheManager:指定缓存管理器；或者指定cacheResolver指定获取解析器
     *      condition:指定符合条件的情况下才缓存
     *                  condition="#a0>1" 如果第一个参数大于1才缓存,即http://localhost:8080/emp/1不缓存
     *      unless:否定缓存；当unless指定的条件伟true,方法的返回值就不会被缓存；可以获取到结果进行判断
     *              unless="#result==null"
     *      sync:是否使用异步模式
     * @param id
     * @return com.atguigu.springboot.bean.Employee
     * @author wangkang
     * 2019/7/14 14:24
     */
    @Cacheable(cacheNames = {"emp"}/*,keyGenerator = "myKeyGenerator",condition="#a0>1"*/)
    public Employee getEmp(Integer id){
        System.out.println("查询"+id+"号员工");
        Employee emp = employeeMapper.getEmpById(id);
        return emp;
    }

    /**
     * @CachePut:即调用方法，又更新缓存数据
     * 修改了数据库的某个数据，同时更新缓存
     * 运行时机:1、先调用目标方法；2、将目标方法的结果缓存起来
     * key-参数employee value-返回的employee
     *          key="#employee.id":使用传入的参数的员工id
     *          key="#result.id":返回值的员工id
     *          @Cacheabnle的key不能用#result:是先查缓存后执行方法
     * @param employee
     * @return com.atguigu.springboot.bean.Employee
     * @author wangkang
     * 2019/7/14 17:34
     */
    @CachePut(value="emp",key="#result.id")
    public Employee updateEmp(Employee employee){
        System.out.println("updateEmp:"+employee);
        employeeMapper.undateEmp(employee);
        return employee;
    }

    /**
     * @CacheEvict：缓存清楚
     *      key：指定要清除的数据
     *      allEntries:是否清空所有缓存内容，缺省为 false，如果true，则方法调用后将立即清空所有缓存
     *      beforeInvocation：缺省为false,缓存清除是在方法之后执行，如果出现异常就不清除缓存，true,是在方法执行之前执行，不管是否出现异常，都会清除缓存
     * @param id
     * @return void
     * @author wangkang
     * 2019/7/14 18:50
     */
    @CacheEvict(value="emp",key="#id")
    public void deleteEmp(Integer id){
        System.out.println("deleteEmp:"+id);
//        employeeMapper.deleteEmpById(id);
    }

    /**
     * @Caching配置复杂的缓存规则
     *      由于有@CachePut的存在，通过lastName查询还是会查数据库
     * @param lastName
     * @return com.atguigu.springboot.bean.Employee
     * @author wangkang
     * 2019/7/14 19:23
     */
    @Caching(
        cacheable = {
            @Cacheable(value="emp",key="#lastName")
        },
        put = {
            @CachePut(value="emp",key="#result.id"),
            @CachePut(value="emp",key="#result.email")
        }
    )
    public Employee getEmpByLastName(String lastName){
        return employeeMapper.getEmpByLastName(lastName);
    }

}
