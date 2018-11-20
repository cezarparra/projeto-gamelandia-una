package org.projeto.gamelandia.simple.permission;

import org.projeto.gamelandia.simple.utils.GenericService;
import org.projeto.gamelandia.simple.utils.ServicePath;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = ServicePath.PERMISSION_PATH)
public class PermissionService extends GenericService<PermissionEntity, Long> {

}
