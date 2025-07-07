package com.dcarrillo.taskmanager.service;

import com.dcarrillo.taskmanager.dto.task.CreateTaskDTO;
import com.dcarrillo.taskmanager.dto.task.TaskDTO;
import com.dcarrillo.taskmanager.entity.Status;
import com.dcarrillo.taskmanager.entity.Task;
import com.dcarrillo.taskmanager.repository.CategoryRepository;
import com.dcarrillo.taskmanager.repository.TaskRepository;
import com.dcarrillo.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;


    public TaskServiceImpl(CategoryRepository categoryRepository, UserRepository userRepository, TaskRepository taskRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Transactional
    @Override
    public TaskDTO createTask(CreateTaskDTO createTaskDTO, Long userId) {
        if (!userRepository.existsUserById(userId)){
            throw new RuntimeException("El usuario no existe");
        }
        Task task = new Task();

        task.setUser(userRepository.findById(userId).orElseThrow());
        task.setCategory(categoryRepository.findByNameAndUser_Id(createTaskDTO.getCategoryName(), userId).orElseThrow());
        task.setStatus(Status.PENDING);
        task.setTitle(createTaskDTO.getTitle());
        task.setDescription(createTaskDTO.getDescription());
        task.setExpirationDate(createTaskDTO.getExpirationDate());

        taskRepository.save(task);

        return getTaskDTO(task);
    }

    @Transactional(readOnly = true)
    @Override
    public TaskDTO findById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(()-> new RuntimeException("Tarea no existente"));

        return getTaskDTO(task);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TaskDTO> findAllByUsername(String username) {
        return taskRepository.findAllByUser(userRepository.findByUsername(username)
                        .orElseThrow(()-> new RuntimeException("Usuario no existe")))
                .stream()
                .map(TaskServiceImpl::getTaskDTO)
                .toList();
    }

    @Override
    public TaskDTO updateTask(Long id, Long userId,  CreateTaskDTO createTaskDTO) {
        Task task = taskRepository.findByIdAndUser_Id(id, userId).orElseThrow(()-> new RuntimeException("Tarea no existe para el Usuario " + userId ));

        task.setCategory(categoryRepository.findByName(createTaskDTO.getCategoryName()));
        task.setTitle(createTaskDTO.getTitle());
        task.setDescription(createTaskDTO.getDescription());
        task.setExpirationDate(createTaskDTO.getExpirationDate());
        taskRepository.save(task);

        return getTaskDTO(task);
    }

    @Override
    public TaskDTO updateStatus(Long id, Long userId, Status status) {
        Task task = taskRepository.findByIdAndUser_Id(id, userId).orElseThrow(()-> new RuntimeException("Tarea no existe para el Usuario " + userId ));
        task.setStatus(status);
        taskRepository.save(task);
        return getTaskDTO(task);
    }

    @Override
    public void deleteTask(Long id, Long userId) {
        Task task = taskRepository.findByIdAndUser_Id(id, userId).orElseThrow(()-> new RuntimeException("Tarea no existe para el Usuario " + userId ));

        taskRepository.delete(task);
    }

    private static TaskDTO getTaskDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setUsername(task.getUser().getUsername());
        taskDTO.setStatus(String.valueOf(task.getStatus()));
        taskDTO.setDescription(task.getDescription());
        if(task.getCategory() != null){
            taskDTO.setCategoryName(task.getCategory().getName());
        }
        taskDTO.setCreationDate(task.getCreationDate());
        taskDTO.setExpirationDate(task.getExpirationDate());
        return taskDTO;
    }
}
