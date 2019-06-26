package br.eti.monzeu.taskprocessor;

import br.eti.monzeu.taskprocessor.service.TaskProcessor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.net.URI;
import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Api(tags = "task")
@RestController
@RequestMapping("/task")
public class TaskCtrl {

  @Autowired
  private TaskProcessor taskProcessor;

  @ApiOperation(value = "Search all tasks")
  @RequestMapping(method = RequestMethod.GET)
  public List<TaskStatus<?>> taskSearch(
      @ApiParam("Tasks modified since (required)") @RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) Instant modifiedSince,
      @ApiParam("Search term. Keywords: code") @RequestParam(required = false) String query) {
    return taskProcessor.search(modifiedSince, query == null ? "" : query);
  }

  @ApiOperation(value = "Get task by code")
  @RequestMapping(value = "/{taskCode}", method = RequestMethod.GET)
  public TaskStatus<?> taskGetByCode(@ApiParam("Task code") @PathVariable String taskCode) {
    return taskProcessor.getByCode(taskCode);
  }

  public URI path(TaskStatus<?> task) {
    return MvcUriComponentsBuilder
        .fromMethodCall(MvcUriComponentsBuilder.on(this.getClass()).taskGetByCode(task.getCode()))
        .build().toUri();
  }
}
