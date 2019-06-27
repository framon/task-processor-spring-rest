package br.eti.monzeu.taskprocessor;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan
@Import(TaskProcessorConfiguration.class)
public class TaskProcessorRestConfiguration {

}
