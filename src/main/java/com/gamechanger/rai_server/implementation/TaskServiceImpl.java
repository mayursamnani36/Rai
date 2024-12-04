package com.gamechanger.rai_server.implementation;

import com.gamechanger.rai_server.entity.TaskEntity;
import com.gamechanger.rai_server.repository.TaskRepository;
import com.gamechanger.rai_server.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, RedisTemplate<String, Object> redisTemplate) {
        this.taskRepository = taskRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void createTask(TaskEntity task) {
        if(task.getId()!=null) redisTemplate.opsForHash().delete("TASK_BY_TASKID", String.valueOf(task.getId()));
        log.info("Task deleted from cache with task id {}", task.getId());
        redisTemplate.opsForHash().delete("TASK_BY_USERID", String.valueOf(task.getAssignee()));
        log.info("Task deleted from cache with user id {}", task.getAssignee());
        redisTemplate.opsForHash().delete("TASK_BY_TAG", task.getTag());
        redisTemplate.delete("TASK_BY_TITLE");
        taskRepository.save(task);
    }

    @Override
    public TaskEntity getTaskByTaskId(Long taskId) {
        TaskEntity task = (TaskEntity) redisTemplate.opsForHash().get("TASK_BY_TASKID", String.valueOf(taskId));
        if(task==null){
            task = taskRepository.findById(taskId).orElse(null);
            if(task!=null) redisTemplate.opsForHash().put("TASK_BY_TASKID", String.valueOf(taskId), task);
        }
        return task;
    }

    @Override
    public List<TaskEntity> getTasksByUserId(Long userId) {
        List<TaskEntity> taskList = (List<TaskEntity>) redisTemplate.opsForHash().get("TASK_BY_USERID", String.valueOf(userId));
        if(taskList==null){
            taskList = taskRepository.getTasksByAssignee(userId);
            redisTemplate.opsForHash().put("TASK_BY_USERID", String.valueOf(userId), taskList);
        }
        return taskList;
    }

    @Override
    public List<TaskEntity> getTasksByTag(String tag) {
        List<TaskEntity> taskList = (List<TaskEntity>) redisTemplate.opsForHash().get("TASK_BY_TAG", tag);
        if(taskList==null){
            taskList = taskRepository.getTasksByTag(tag);
            redisTemplate.opsForHash().put("TASK_BY_TAG", tag, taskList);
        }
        return taskList;
    }

    @Override
    public List<TaskEntity> searchTasksByTitle(String title) {
        List<TaskEntity> taskList = (List<TaskEntity>) redisTemplate.opsForHash().get("TASK_BY_TITLE", title);
        if(taskList==null){
            taskList = taskRepository.findByTitleContaining(title);
            redisTemplate.opsForHash().put("TASK_BY_TITLE", title, taskList);
        }
        return taskList;
    }
}
