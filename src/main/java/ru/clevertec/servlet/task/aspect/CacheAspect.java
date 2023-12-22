package ru.clevertec.servlet.task.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import ru.clevertec.servlet.task.cache.Cache;
import ru.clevertec.servlet.task.config.LoadProperties;
import ru.clevertec.servlet.task.entity.dto.ClientDto;
import ru.clevertec.servlet.task.entity.model.Client;
import ru.clevertec.servlet.task.mapper.ClientMapper;
import ru.clevertec.servlet.task.mapper.ClientMapperImpl;
import ru.clevertec.servlet.task.util.Constants;

@Aspect
public class CacheAspect {

    private final Cache cache;
    private final ClientMapper clientMapper;

    {
        cache = Constants.CACHE;
        cache.setSizeCache(Integer.valueOf(LoadProperties.getProperties().getProperty(Constants.CACHE_SIZE)));
        clientMapper = new ClientMapperImpl();
    }

    @Pointcut("@annotation(ru.clevertec.servlet.task.aspect.annotation.SaveClient)")
    public void saveMethod() {
    }

    @Pointcut("@annotation(ru.clevertec.servlet.task.aspect.annotation.DeleteClient)")
    public void deleteMethod() {
    }

    @Pointcut("@annotation(ru.clevertec.servlet.task.aspect.annotation.GetClient)")
    public void getMethod() {
    }

    @Pointcut("@annotation(ru.clevertec.servlet.task.aspect.annotation.UpdateClient)")
    public void updateMethod() {
    }

    /**
     * Вызывает метод сохранения полученного объекта в кэш.
     */
    @Around(value = "saveMethod()")
    public Object doSaveProfiling(ProceedingJoinPoint pjp) throws Throwable {
        Client client = (Client) pjp.proceed();
        cache.save(client.getId(), client);
        return client;
    }

    /**
     * Вызывает метод удаления объекта из кэша по id.
     */
    @Around(value = "deleteMethod()")
    public Object doDeleteProfiling(ProceedingJoinPoint pjp) throws Throwable {
        Long idForDelete = (Long) pjp.getArgs()[0];
        pjp.proceed();
        cache.delete(idForDelete);
        return idForDelete;
    }

    /**
     * Получает объект из кеша по id, если такой там есть.
     * Если такого объекта по id в кэше нет - вызывает метод
     * получения объекта из базы данных.
     */
    @Around(value = "getMethod()")
    public Object doGetProfiling(ProceedingJoinPoint pjp) throws Throwable {
        Long idForGet = (Long) pjp.getArgs()[0];
        Client client = null;
        client = (Client) cache.getById(idForGet);
        if (client == null) {
            client = (Client) pjp.proceed();
            cache.save(client.getId(), client);
        }
        return client;
    }

    /**
     * Вызывает метод обновления переданного объекта в кэше.
     */
    @Around(value = "updateMethod()")
    public Object doUpdateProfiling(ProceedingJoinPoint pjp) throws Throwable {
        Long idForUpdate = (Long) pjp.getArgs()[0];
        ClientDto clientDto = (ClientDto) pjp.getArgs()[1];
        Client client = clientMapper.clientDtoToClient(clientDto);
        client.setId(idForUpdate);
        pjp.proceed();
        cache.save(idForUpdate, client);
        return client;
    }

}