package com.dcarrillo.taskmanager.service;

import com.dcarrillo.taskmanager.dto.task.CreateTaskDTO;
import com.dcarrillo.taskmanager.dto.task.TaskDTO;
import com.dcarrillo.taskmanager.dto.task.UpdateTaskDTO;
import com.dcarrillo.taskmanager.entity.Category;
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

        if (createTaskDTO.getCategoryName() != null && createTaskDTO.getCategoryName().isEmpty()){
            task.setCategory(categoryRepository.findByNameAndUser_Id( createTaskDTO.getCategoryName(), userId)
                    .orElseGet(()->{
                        Category newCategory = new Category();
                        newCategory.setName(createTaskDTO.getCategoryName());
                        newCategory.setUser(userRepository.findById(userId).orElseThrow());
                        return categoryRepository.save(newCategory);
                    }));
        }else {
            task.setCategory(categoryRepository.findByNameAndUser_Id("Sin Categoria", userId).orElseThrow());
        }
        task.setStatus(Status.PENDING);
        task.setTitle(createTaskDTO.getTitle());
        task.setDescription(createTaskDTO.getDescription());
        task.setExpirationDate(createTaskDTO.getExpirationDate());

        taskRepository.save(task);

        return getTaskDTO(task);
    }

    @Transactional(readOnly = true)
    @Override
    public TaskDTO findById(Long id, Long userId) {
        Task task = taskRepository.findByIdAndUser_Id(id, userId).orElseThrow(()-> new RuntimeException("Tarea no existente"));

        return getTaskDTO(task);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TaskDTO> findAllByUserId(Long userId) {
        return taskRepository.findAllByUser(userRepository.findById(userId)
                        .orElseThrow(()-> new RuntimeException("Usuario no existe")))
                .stream()
                .map(TaskServiceImpl::getTaskDTO)
                .toList();
    }

    @Transactional
    @Override
    public TaskDTO updateTask(Long id, Long userId,  UpdateTaskDTO updateTaskDTO) {
        Task task = taskRepository.findByIdAndUser_Id(id, userId).orElseThrow(()-> new RuntimeException("Tarea no existe para el Usuario " + userId ));

        task.setCategory(categoryRepository.findByNameAndUser_Id(updateTaskDTO.getCategory(), userId).orElseThrow(()->new RuntimeException("categoria no encontrada")));
        task.setTitle(updateTaskDTO.getTitle());
        task.setDescription(updateTaskDTO.getDescription());
        task.setStatus(Status.valueOf(updateTaskDTO.getStatus()));
        task.setExpirationDate(updateTaskDTO.getExpirationDate());
        taskRepository.save(task);

        return getTaskDTO(task);
    }

    @Transactional
    @Override
    public void deleteTask(Long id, Long userId) {
        Task task = taskRepository.findByIdAndUser_Id(id, userId).orElseThrow(()-> new RuntimeException("Tarea no existe para el Usuario " + userId ));

        taskRepository.delete(task);
    }

    private static TaskDTO getTaskDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setUsername(task.getUser().getUsername());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setStatus(String.valueOf(task.getStatus()));
        taskDTO.setDescription(task.getDescription());
        taskDTO.setCategoryName(task.getCategory().getName());
        taskDTO.setCreationDate(task.getCreationDate());
        taskDTO.setExpirationDate(task.getExpirationDate());
        return taskDTO;
    }
}
