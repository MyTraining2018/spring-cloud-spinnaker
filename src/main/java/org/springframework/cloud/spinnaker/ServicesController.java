/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.cloud.spinnaker;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.net.URL;

import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Greg Turnquist
 */
@RestController
public class ServicesController {

	private final ServicesService servicesService;

	public ServicesController(ServicesService servicesService) {
		this.servicesService = servicesService;
	}

	@RequestMapping(method = RequestMethod.GET, value = ApiController.BASE_PATH + "/services", produces = MediaTypes.HAL_JSON_VALUE)
	public ResponseEntity<?> listServices(@RequestParam(value = "serviceType", defaultValue = "") String serviceType,
										  @RequestParam("api") URL apiEndpoint,
										  @RequestParam("org") String org,
										  @RequestParam("space") String space,
										  @RequestParam("email") String email,
										  @RequestParam("password") String password) {

		return ResponseEntity.ok(new Resources<>(
			this.servicesService.getServices(serviceType, email, password, apiEndpoint, org, space),
			linkTo(methodOn(ServicesController.class).listServices(serviceType, apiEndpoint, org, space, email, password)).withSelfRel()));
	}

	@RequestMapping(method = RequestMethod.GET, value = ApiController.BASE_PATH + "/orgs", produces = MediaTypes.HAL_JSON_VALUE)
	public ResponseEntity<?> listOrgs(@RequestParam("api") URL apiEndpoint,
									  @RequestParam("email") String email,
									  @RequestParam("password") String password) {

		return ResponseEntity.ok(new Resource<>(
			this.servicesService.getOrgs(email, password, apiEndpoint),
			linkTo(methodOn(ServicesController.class).listOrgs(apiEndpoint, email, password)).withSelfRel()));
	}

	@RequestMapping(method = RequestMethod.GET, value = ApiController.BASE_PATH + "/domains", produces = MediaTypes.HAL_JSON_VALUE)
	public ResponseEntity<?> listDomains(@RequestParam("api") URL apiEndpoint,
										 @RequestParam("email") String email,
										 @RequestParam("password") String password,
										 @RequestParam("org") String org,
										 @RequestParam("space") String space) {

		return ResponseEntity.ok(new Resources<>(
			this.servicesService.getDomains(email, password, apiEndpoint, org, space),
			linkTo(methodOn(ServicesController.class).listDomains(apiEndpoint, email, password, org, space)).withSelfRel()));
	}

}
