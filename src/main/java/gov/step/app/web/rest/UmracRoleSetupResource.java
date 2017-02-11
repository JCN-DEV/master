package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
//import com.google.gson.*;
import gherkin.deps.com.google.gson.*;
import gov.step.app.domain.*;
import gov.step.app.repository.*;
import gov.step.app.repository.search.UmracRightsSetupSearchRepository;
import gov.step.app.repository.search.UmracRoleSetupSearchRepository;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.util.DateResource;
import gov.step.app.web.rest.util.HeaderUtil;
import gov.step.app.web.rest.util.PaginationUtil;

import gov.step.app.web.rest.util.TransactionIdResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing UmracRoleSetup.
 */
@RestController
@RequestMapping("/api")
public class UmracRoleSetupResource {

    private final Logger log = LoggerFactory.getLogger(UmracRoleSetupResource.class);

    @Inject
    private UmracRoleSetupRepository umracRoleSetupRepository;

    @Inject
    private UmracModuleSetupRepository umracModuleSetupRepository;

    @Inject
    private UmracSubmoduleSetupRepository umracSubmoduleSetupRepository;

    @Inject
    private UmracActionSetupRepository umracActionSetupRepository;

    @Inject
    private UmracRightsSetupRepository umracRightsSetupRepository;

    @Inject
    private UmracRoleSetupSearchRepository umracRoleSetupSearchRepository;

    @Inject
    private UmracIdentitySetupRepository umracIdentitySetupRepository;

    @Inject
    private UmracRoleAssignUserRepository umracRoleAssignUserRepository;


    /**
     * POST  /umracRoleSetups -> Create a new umracRoleSetup.
     */
    @RequestMapping(value = "/umracRoleSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmracRoleSetup> createUmracRoleSetup(@Valid @RequestBody UmracRoleSetup umracRoleSetup) throws URISyntaxException {
        log.debug("REST request to save UmracRoleSetup : {}", umracRoleSetup);
        if (umracRoleSetup.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new umracRoleSetup cannot already have an ID").body(null);
        }
        String rightsList = umracRoleSetup.getRoleContext();
        umracRoleSetup.setRoleId("");

        String[] splitArray = null;
        try {
            splitArray = rightsList.split(",");
        } catch (PatternSyntaxException ex) {
            System.out.println("\n Exception : " + ex);
        }

        String userName = SecurityUtils.getCurrentUserLogin();
        Long userId = SecurityUtils.getCurrentUserId();

        umracRoleSetup.setCreateBy(userId);
        TransactionIdResource tir = new TransactionIdResource();
        umracRoleSetup.setRoleId(tir.getGeneratedid("ROLE"));
        DateResource dr = new DateResource();
        umracRoleSetup.setCreateDate(dr.getDateAsLocalDate());

        UmracRoleSetup result = umracRoleSetupRepository.save(umracRoleSetup);
        if (result.getId() != null) {
            String rightsPermission = "";
            Integer moduleCount = 0;
            String moduleId = "";
            String subModuleId = "";

            for (String loopRights : splitArray) {
                String[] splitRight = null;
                //Integer counterOfRights = 0;
                try {
                    splitRight = loopRights.split("-");
                } catch (PatternSyntaxException ex) {
                    //
                }
                /*if (Integer.parseInt(splitRight[2].toString()) < 8) {
                    rightsPermission += splitRight[2].toString() + ",";
                    moduleCount+=1;
                }

                if (moduleCount > 1 && Integer.parseInt(splitRight[2].toString()) == 1) {
                //if (moduleCount > 1 import com.google.gson.*;&& Integer.parseInt(splitRight[2].toString()) == 1) {
                    UmracRightsSetup umracRightsSetup = new UmracRightsSetup();
                    TransactionIdResource right = new TransactionIdResource();
                    umracRightsSetup.setRightId(right.getGeneratedid("RGT"));
                    umracRightsSetup.setCreateBy(Long.parseLong("1"));
                    DateResource drRight = new DateResource();
                    umracRightsSetup.setCreateDate(drRight.getDateAsLocalDate());
                    umracRightsSetup.setRights(splitRight[2].toString());
                    umracRightsSetup.setModule_id(Long.parseLong(splitRight[0].toString()));
                    umracRightsSetup.setSubModule_id(Long.parseLong(splitRight[1].toString()));
                    umracRightsSetup.setRoleId(result.getId());
                    umracRightsSetup.setStatus(true);
                    umracRightsSetup.setDescription(rightsPermission.substring(0, rightsPermission.length() - 1));
                    umracRightsSetupRepository.save(umracRightsSetup);
                    rightsPermission = "";
                }*/
                UmracRightsSetup umracRightsSetup = new UmracRightsSetup();
                TransactionIdResource right = new TransactionIdResource();
                umracRightsSetup.setRightId(right.getGeneratedid("RGT"));
                umracRightsSetup.setCreateBy(Long.parseLong("1"));
                DateResource drRight = new DateResource();
                umracRightsSetup.setCreateDate(drRight.getDateAsLocalDate());
                umracRightsSetup.setRights(splitRight[2].toString());
                umracRightsSetup.setModule_id(Long.parseLong(splitRight[0].toString()));
                umracRightsSetup.setSubModule_id(Long.parseLong(splitRight[1].toString()));
                umracRightsSetup.setRoleId(result.getId());
                umracRightsSetup.setStatus(true);
                umracRightsSetup.setDescription(loopRights);
                umracRightsSetupRepository.save(umracRightsSetup);
            }
        }

        umracRoleSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/umracRoleSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("umracRoleSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /umracRoleSetups -> Updates an existing umracRoleSetup.
     */
    @RequestMapping(value = "/umracRoleSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmracRoleSetup> updateUmracRoleSetup(@Valid @RequestBody UmracRoleSetup umracRoleSetup) throws URISyntaxException {
        log.debug("REST request to update UmracRoleSetup : {}", umracRoleSetup);
        if (umracRoleSetup.getId() == null) {
            return createUmracRoleSetup(umracRoleSetup);
        }

        String rightsList = umracRoleSetup.getRoleContext();
        umracRoleSetup.setRoleId("");

        String[] splitArray = null;
        try {
            splitArray = rightsList.split(",");
        } catch (PatternSyntaxException ex) {
            System.out.println("\n Exception : " + ex);
        }

        String userName = SecurityUtils.getCurrentUserLogin();
        Long userId = SecurityUtils.getCurrentUserId();

        umracRoleSetup.setUpdatedBy(userId);
        DateResource dr = new DateResource();
        umracRoleSetup.setUpdatedTime(dr.getDateAsLocalDate());

        UmracRoleSetup result = umracRoleSetupRepository.save(umracRoleSetup);
        if (result.getId() != null) {
            String rightsPermission = "";
            Integer moduleCount = 0;
            String moduleId = "";
            String subModuleId = "";

            for (String loopRights : splitArray) {
                String[] splitRight = null;
                //Integer counterOfRights = 0;
                try {
                    splitRight = loopRights.split("-");
                } catch (PatternSyntaxException ex) {
                    //
                }

                UmracRightsSetup umracRightsSetup = new UmracRightsSetup();
                TransactionIdResource right = new TransactionIdResource();
                umracRightsSetup.setRightId(right.getGeneratedid("RGT"));
                umracRightsSetup.setCreateBy(Long.parseLong("1"));
                DateResource drRight = new DateResource();
                umracRightsSetup.setCreateDate(drRight.getDateAsLocalDate());
                umracRightsSetup.setRights(splitRight[2].toString());
                umracRightsSetup.setModule_id(Long.parseLong(splitRight[0].toString()));
                umracRightsSetup.setSubModule_id(Long.parseLong(splitRight[1].toString()));
                umracRightsSetup.setRoleId(result.getId());
                umracRightsSetup.setStatus(true);
                umracRightsSetup.setDescription(loopRights);
                //Need remove then add GotIt
                umracRightsSetupRepository.save(umracRightsSetup);
            }
        }
        umracRoleSetupSearchRepository.save(umracRoleSetup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("umracRoleSetup", umracRoleSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /umracRoleSetups -> get all the umracRoleSetups.
     */
    @RequestMapping(value = "/umracRoleSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<UmracRoleSetup>> getAllUmracRoleSetups(Pageable pageable)
        throws URISyntaxException {
        Page<UmracRoleSetup> page = umracRoleSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/umracRoleSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /umracRoleSetups/:id -> get the "id" umracRoleSetup.
     */
    @RequestMapping(value = "/umracRoleSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmracRoleSetup> getUmracRoleSetup(@PathVariable Long id) {
        log.debug("REST request to get UmracRoleSetup : {}", id);
        return Optional.ofNullable(umracRoleSetupRepository.findOne(id))
            .map(umracRoleSetup -> new ResponseEntity<>(
                umracRoleSetup,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /umracRoleSetups/ -> get the "JSON" umracRoleSetup.
     */
    @RequestMapping(value = "/umracRoleSetups/rolesRights",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public String getUmracRoleRightsTree() {
        log.debug("REST request to get RolesRightsTree");
        //generateAllRightsJson();
        //String jsondatas="[{\"name\": \"Human Resource Module\",\"code\": \"1\",\"checked\": false,\"children\": [{\"name\": \"Employee Profile\",\"code\": \"1-1\",\"checked\": false,\"children\": [{\"name\": \"Add\",\"checked\": false,\"code\": \"1-1-1\"},{\"name\": \"View\",\"checked\": false,\"code\": \"1-1-2\"},{\"name\": \"Update\",\"checked\": false,\"code\": \"1-1-3\"},{\"name\": \"Delete\",\"checked\": false,\"code\": \"1-1-4\"},{\"name\": \"Export\",\"checked\": false,\"code\": \"1-1-5\"},{\"name\": \"Import\",\"checked\": false,\"code\": \"1-1-6\"},{\"name\": \"Report\",\"checked\": false,\"code\": \"1-1-7\"}]}]}]";
        String jsondatas = generateAllRightsJson();
        return jsondatas;
    }

    /**
     * GET  /umracRoleSetups/ -> get the "JSON" umracRoleSetup.
     */
    @RequestMapping(value = "/umracRoleSetups/rolesRightsByRoleId/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public String getUmracRoleRightsTreeByRoleId(@PathVariable Long id) {
        log.debug("REST request to get RolesRightsTree BY ROLE ID ---> " + id);
        String jsondatas = generateRightsJsonByRoleId(id);
        return jsondatas;
    }

    /**
     * GET  /umracRoleSetups/ -> get the "JSON" umracRoleSetup.
     */
    @RequestMapping(value = "/umracRoleSetups/rolesRightsById/{userId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public String getUmracRoleRightsTreeById(@PathVariable String userId) {
        log.debug("REST request to get RolesRightsTree BY ID ---> " + userId);
        String jsondatas = generateRightsJsonByUSerID(userId);
        return jsondatas;
    }

    /**
     * GET  /umracRoleSetups/ -> get the "ROLEID" umracRoleSetup.
     */
    @RequestMapping(value = "/umracRoleSetups/roleIdById/{userId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public String getUmracRoleIdById(@PathVariable String userId) {
        log.debug("REST request to get RolesRightsTree BY ID ---> " + userId);
        String jsondatas = getRoleIdByUSerID(userId);
        return jsondatas;
    }

    /**
     * GET  /umracRoleSetups/ -> get the "ROLEID" umracRoleSetup.
     */
    @RequestMapping(value = "/umracRoleSetups/roleActionById/{userId}/{roleId}/{moduleId}/{subModuleId}/{actionId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public String getUmracRoleActionById(@PathVariable Long userId,@PathVariable Long roleId,@PathVariable Long moduleId,@PathVariable Long subModuleId,@PathVariable Long actionId) {
        log.debug("REST request to get MODULE WISE ---> " + userId);
        String jsondatas = getRoleActionByUSerID(userId, roleId, moduleId,subModuleId, actionId);
        log.debug("REST request to get MODULE WISE STATUS ---> " + jsondatas);
        return jsondatas;
    }

    /**
     * DELETE  /umracRoleSetups/:id -> delete the "id" umracRoleSetup.
     */
    @RequestMapping(value = "/umracRoleSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUmracRoleSetup(@PathVariable Long id) {
        log.debug("REST request to delete UmracRoleSetup : {}", id);
        umracRoleSetupRepository.delete(id);
        umracRoleSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("umracRoleSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/umracRoleSetups/:query -> search for the umracRoleSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/umracRoleSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<UmracRoleSetup> searchUmracRoleSetups(@PathVariable String query) {
        return StreamSupport
            .stream(umracRoleSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/umracRoleSetups/roleName/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getRoleByName(@RequestParam String value) {

        log.debug("REST request to get umracRoleSetups by name : {}", value);

        Optional<UmracRoleSetup> umracRoleSetup = umracRoleSetupRepository.findOneByRoleName(value);

       /* log.debug("user on check for----"+value);
        log.debug("cmsCurriculum on check code----"+Optional.empty().equals(cmsCurriculum));*/
        //generateAllRightsJson();
        Map map = new HashMap();

        map.put("value", value);

        if (Optional.empty().equals(umracRoleSetup)) {
            map.put("isValid", true);
            return new ResponseEntity<Map>(map, HttpStatus.OK);

        } else {
            map.put("isValid", false);
            return new ResponseEntity<Map>(map, HttpStatus.OK);
        }
    }

    // All JSON Generate
    private String generateAllRightsJson() {
        String TreeJson = "";
        JsonArray trees = new JsonArray();
        try {

            List<UmracModuleSetup> umracModuleSetups = umracModuleSetupRepository.findAll();

            for (UmracModuleSetup m : umracModuleSetups) {
                JsonObject tree = new JsonObject();
                tree.addProperty("name", m.getModuleName());
                tree.addProperty("code", m.getId());
                tree.addProperty("checked", false); // For Edit -> getModuleByRoleID -> rightsSetup, For User - > RoldeId -> -> getModuleByRoleID -> rightsSetup
                JsonArray moduleChilderns = new JsonArray();
                UmracModuleSetup umodule = new UmracModuleSetup();
                umodule.setId(m.getId());
                List<UmracSubmoduleSetup> umracSubmoduleSetups = umracSubmoduleSetupRepository.findByModuleId(umodule);

                for (UmracSubmoduleSetup s : umracSubmoduleSetups) {

                    JsonObject moduleChildern = new JsonObject();
                    moduleChildern.addProperty("name", s.getSubModuleName());
                    moduleChildern.addProperty("code", m.getId() + "-" + s.getId());
                    moduleChildern.addProperty("checked", false); // For Edit -> getModuleByRoleID -> rightsSetup, For User - > RoldeId -> -> getModuleByRoleID -> rightsSetup
                    JsonArray subModuleChilderns = new JsonArray();

                    List<UmracActionSetup> umracActionSetups = umracActionSetupRepository.findAllActionId();
                    for (UmracActionSetup a : umracActionSetups) {
                        // Here JSON generate
                        JsonObject subModuleChildern = new JsonObject();
                        subModuleChildern.addProperty("name", a.getActionName());
                        subModuleChildern.addProperty("code", m.getId() + "-" + s.getId() + "-" + a.getActionId());
                        subModuleChildern.addProperty("checked", false); // For Edit -> getModuleByRoleID -> rightsSetup, For User - > RoldeId -> -> getModuleByRoleID -> rightsSetup
                        subModuleChilderns.add(subModuleChildern);
                    }
                    moduleChildern.add("children", subModuleChilderns);

                    moduleChilderns.add(moduleChildern);
                }

                tree.add("children", moduleChilderns);

                trees.add(tree);
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
            TreeJson = gson.toJson(trees);
            //System.out.println(TreeJson);
        } catch (Exception ex) {
            log.debug("Exception in Tree Generate", ex);
        }
        return TreeJson;
    }

    private String generateRightsJsonByRoleId(Long roleId) {
        String TreeJson = "";
        JsonArray trees = new JsonArray();

        // Add code for get RoleId wise role
        //System.out.println("\n ROLE ID ---> " + roleId);

        try {

            List<UmracModuleSetup> umracModuleSetups = umracModuleSetupRepository.findAll();

            for (UmracModuleSetup m : umracModuleSetups) {

                JsonObject tree = new JsonObject();
                tree.addProperty("name", m.getModuleName());
                tree.addProperty("code", m.getId());

                List<UmracRightsSetup> umracRightsSetup = null;
                //System.out.println("\n OUTPUTS ---> " + umracRightsSetupRepository.findOneByRoleIdAndModuleId(roleId, m.getId()));
                umracRightsSetup = umracRightsSetupRepository.findOneByRoleIdAndModuleId(roleId, m.getId());

                if (umracRightsSetup.size() > 0) {
                    //System.out.println("\n Module ID ---> " + roleId + " --- " + m.getId() + " --- " + umracRightsSetup);
                    tree.addProperty("checked", true);
                } else {
                    tree.addProperty("checked", false);
                }

                JsonArray moduleChilderns = new JsonArray();
                UmracModuleSetup umodule = new UmracModuleSetup();
                umodule.setId(m.getId());
                List<UmracSubmoduleSetup> umracSubmoduleSetups = umracSubmoduleSetupRepository.findByModuleId(umodule);

                for (UmracSubmoduleSetup s : umracSubmoduleSetups) {

                    JsonObject moduleChildern = new JsonObject();
                    moduleChildern.addProperty("name", s.getSubModuleName());
                    moduleChildern.addProperty("code", m.getId() + "-" + s.getId());

                    List<UmracRightsSetup> umracRightsSetupSubmodule = null;
                    umracRightsSetupSubmodule = umracRightsSetupRepository.findOneByRoleIdAndSubModuleId(roleId, m.getId(), s.getId());

                    if (umracRightsSetupSubmodule.size() > 0) {
                        //System.out.println("\n SubModule ID ---> " + roleId +" --- "+m.getId()+" --- "+s.getId()+" --- "+umracRightsSetupSubmodule);
                        moduleChildern.addProperty("checked", true);
                    } else {
                        moduleChildern.addProperty("checked", false);
                    }

                    JsonArray subModuleChilderns = new JsonArray();

                    List<UmracActionSetup> umracActionSetups = umracActionSetupRepository.findAllActionId();
                    for (UmracActionSetup a : umracActionSetups) {

                        // Here JSON generate
                        JsonObject subModuleChildern = new JsonObject();
                        subModuleChildern.addProperty("name", a.getActionName());
                        subModuleChildern.addProperty("code", m.getId() + "-" + s.getId() + "-" + a.getActionId());

                        List<UmracRightsSetup> umracRightsSetupActions = null;
                        umracRightsSetupActions = umracRightsSetupRepository.findOneByRoleIdAndActionsId(roleId, m.getId(), s.getId(), a.getActionId());
                        if (umracRightsSetupActions.size() > 0) {
                            //System.out.println("\n Actions ID ---> " + roleId +" --- "+m.getId()+" --- "+s.getId()+" --- "+a.getActionId()+" --- "+umracRightsSetupActions);
                            subModuleChildern.addProperty("checked", true);
                        } else {
                            subModuleChildern.addProperty("checked", false);
                        }


                        subModuleChilderns.add(subModuleChildern);
                    }
                    moduleChildern.add("children", subModuleChilderns);

                    moduleChilderns.add(moduleChildern);
                }

                tree.add("children", moduleChilderns);

                trees.add(tree);
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
            TreeJson = gson.toJson(trees);
            //System.out.println(TreeJson);
        } catch (Exception ex) {
            log.debug("Exception in Tree Generate", ex);
        }
        return TreeJson;
    }

    private String generateRightsJsonByRoleName(String roleName) {
        String TreeJson = "";
        JsonArray trees = new JsonArray();

        // Add code for get UserID wise role
        try {

            List<UmracModuleSetup> umracModuleSetups = umracModuleSetupRepository.findAll();

            for (UmracModuleSetup m : umracModuleSetups) {
                JsonObject tree = new JsonObject();
                tree.addProperty("name", m.getModuleName());
                tree.addProperty("code", m.getId());
                tree.addProperty("checked", false); // For Edit -> getModuleByRoleID -> rightsSetup, For User - > RoldeId -> -> getModuleByRoleID -> rightsSetup
                JsonArray moduleChilderns = new JsonArray();
                UmracModuleSetup umodule = new UmracModuleSetup();
                umodule.setId(m.getId());
                List<UmracSubmoduleSetup> umracSubmoduleSetups = umracSubmoduleSetupRepository.findByModuleId(umodule);

                for (UmracSubmoduleSetup s : umracSubmoduleSetups) {

                    JsonObject moduleChildern = new JsonObject();
                    moduleChildern.addProperty("name", s.getSubModuleName());
                    moduleChildern.addProperty("code", m.getId() + "-" + s.getId());
                    moduleChildern.addProperty("checked", false); // For Edit -> getModuleByRoleID -> rightsSetup, For User - > RoldeId -> -> getModuleByRoleID -> rightsSetup
                    JsonArray subModuleChilderns = new JsonArray();

                    List<UmracActionSetup> umracActionSetups = umracActionSetupRepository.findAllActionId();
                    for (UmracActionSetup a : umracActionSetups) {
                        // Here JSON generate
                        JsonObject subModuleChildern = new JsonObject();
                        subModuleChildern.addProperty("name", a.getActionName());
                        subModuleChildern.addProperty("code", m.getId() + "-" + s.getId() + "-" + a.getActionId());
                        subModuleChildern.addProperty("checked", false); // For Edit -> getModuleByRoleID -> rightsSetup, For User - > RoldeId -> -> getModuleByRoleID -> rightsSetup
                        subModuleChilderns.add(subModuleChildern);
                    }
                    moduleChildern.add("children", subModuleChilderns);

                    moduleChilderns.add(moduleChildern);
                }

                tree.add("children", moduleChilderns);

                trees.add(tree);
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
            TreeJson = gson.toJson(trees);
            //System.out.println(TreeJson);
        } catch (Exception ex) {
            log.debug("Exception in Tree Generate", ex);
        }
        return TreeJson;
    }

    private String generateRightsJsonByUSerID(String userIds) {
        String TreeJson = "";
        JsonArray trees = new JsonArray();


        System.out.println("\n userIds ---> " + userIds);
        List<UmracIdentitySetup> umracIdentitySetup = umracIdentitySetupRepository.findByUserName(userIds);
        Long roleId = null;

        for (UmracIdentitySetup u : umracIdentitySetup) {
            System.out.println("\n umracIdentitySetup.getId()clear ---> " + u.getId());
            UmracIdentitySetup userTypeId = new UmracIdentitySetup();
            userTypeId.setId(u.getId());
            List<UmracRoleAssignUser> umracRoleAssignUser = umracRoleAssignUserRepository.findOneByUserId(userTypeId);
            for (UmracRoleAssignUser ur : umracRoleAssignUser) {
                roleId = ur.getRoleId().getId();
                System.out.println("\n ROLE ID ---> " + roleId);
            }

        }

        if (roleId == null) {
            TreeJson = generateAllRightsJson();
        } else if (roleId != null) {
            try {

                List<UmracModuleSetup> umracModuleSetups = umracModuleSetupRepository.findAll();

                for (UmracModuleSetup m : umracModuleSetups) {

                    JsonObject tree = new JsonObject();
                    tree.addProperty("name", m.getModuleName());
                    tree.addProperty("code", m.getId());

                    //if(roleId != null){
                    List<UmracRightsSetup> umracRightsSetup = null;
                    umracRightsSetup = umracRightsSetupRepository.findOneByRoleIdAndModuleId(roleId, m.getId());
                    if (umracRightsSetup.size() > 0) {
                        //System.out.println("\n Module ID ---> " + roleId +" --- "+m.getId()+" --- "+umracRightsSetup);
                        tree.addProperty("checked", true);
                    } else {
                        tree.addProperty("checked", false);
                    }


                    JsonArray moduleChilderns = new JsonArray();
                    UmracModuleSetup umodule = new UmracModuleSetup();
                    umodule.setId(m.getId());
                    List<UmracSubmoduleSetup> umracSubmoduleSetups = umracSubmoduleSetupRepository.findByModuleId(umodule);

                    for (UmracSubmoduleSetup s : umracSubmoduleSetups) {

                        JsonObject moduleChildern = new JsonObject();
                        moduleChildern.addProperty("name", s.getSubModuleName());
                        moduleChildern.addProperty("code", m.getId() + "-" + s.getId());

                        //if(roleId != null){
                        List<UmracRightsSetup> umracRightsSetupSubmodule = null;
                        umracRightsSetupSubmodule = umracRightsSetupRepository.findOneByRoleIdAndSubModuleId(roleId, m.getId(), s.getId());
                        if (umracRightsSetupSubmodule.size() > 0) {
                            //System.out.println("\n SubModule ID ---> " + roleId +" --- "+m.getId()+" --- "+s.getId()+" --- "+umracRightsSetupSubmodule);
                            moduleChildern.addProperty("checked", true);
                        } else {
                            moduleChildern.addProperty("checked", false);
                        }


                        JsonArray subModuleChilderns = new JsonArray();

                        List<UmracActionSetup> umracActionSetups = umracActionSetupRepository.findAllActionId();
                        for (UmracActionSetup a : umracActionSetups) {

                            // Here JSON generate
                            JsonObject subModuleChildern = new JsonObject();
                            subModuleChildern.addProperty("name", a.getActionName());
                            subModuleChildern.addProperty("code", m.getId() + "-" + s.getId() + "-" + a.getActionId());

                            //if(roleId != null){
                            List<UmracRightsSetup> umracRightsSetupActions = null;
                            umracRightsSetupActions = umracRightsSetupRepository.findOneByRoleIdAndActionsId(roleId, m.getId(), s.getId(), a.getActionId());
                            if (umracRightsSetupActions.size() > 0) {
                                //System.out.println("\n Actions ID ---> " + roleId +" --- "+m.getId()+" --- "+s.getId()+" --- "+a.getActionId()+" --- "+umracRightsSetupActions);
                                subModuleChildern.addProperty("checked", true);
                            } else {
                                subModuleChildern.addProperty("checked", false);
                            }

                            subModuleChilderns.add(subModuleChildern);
                        }
                        moduleChildern.add("children", subModuleChilderns);

                        moduleChilderns.add(moduleChildern);
                    }

                    tree.add("children", moduleChilderns);

                    trees.add(tree);
                }

                Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
                TreeJson = gson.toJson(trees);
                //System.out.println(TreeJson);
            } catch (Exception ex) {
                log.debug("Exception in Tree Generate", ex);
            }
        }
        return TreeJson;

    }

    private String getRoleIdByUSerID(String userIds) {
        String TreeJson = "";
        JsonArray trees = new JsonArray();
        JsonObject tree = new JsonObject();

        List<UmracIdentitySetup> umracIdentitySetup = umracIdentitySetupRepository.findByUserName(userIds);
        Long roleId = null;

        for (UmracIdentitySetup u : umracIdentitySetup) {
            UmracIdentitySetup userTypeId = new UmracIdentitySetup();
            userTypeId.setId(u.getId());
            tree.addProperty("userid", u.getId());
            tree.addProperty("username", u.getUserName());
            List<UmracRoleAssignUser> umracRoleAssignUser = umracRoleAssignUserRepository.findOneByUserId(userTypeId);
            for (UmracRoleAssignUser ur : umracRoleAssignUser) {
                roleId = ur.getRoleId().getId();
                tree.addProperty("userroleid", String.valueOf(ur.getRoleId().getId()));
            }

        }

        if (roleId != null) {
            try {
                trees.add(tree);
                Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
                TreeJson = gson.toJson(trees);
            } catch (Exception ex) {
                log.debug("Exception in TreeAboutRoles Generate", ex);
            }
        }else{
            try {
                tree.addProperty("userid", "NotValid");
                tree.addProperty("username", "NotValid");
                tree.addProperty("userroleid", "NotValid");
                trees.add(tree);
                Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
                TreeJson = gson.toJson(trees);
            } catch (Exception ex) {
                log.debug("Exception in TreeAboutRoles Generate", ex);
            }

        }

        return TreeJson;
    }


    private String getRoleActionByUSerID(Long userIds,Long roleId,Long  moduleId,Long subModuleId,Long actionId) {
        Boolean Status = false;
        String TreeJson = "";
        JsonArray trees = new JsonArray();
        JsonObject tree = new JsonObject();

        UmracIdentitySetup umracIdentitySetupId = new UmracIdentitySetup();
        umracIdentitySetupId.setId(userIds);

        List<UmracRoleAssignUser> umracRoleAssignUsers = umracRoleAssignUserRepository.findOneByUserId(umracIdentitySetupId);

        //String sessionRoleId = roleId.toString();
        //String queriedRoleId = umracRoleAssignUsers.getRoleId().toString();
        //log.debug("REST request to get ROLE WISE STATUS ---> " + sessionRoleId + " @@@@@@ "+queriedRoleId);

        if(umracRoleAssignUsers != null && umracRoleAssignUsers.size() > 0){

            List<UmracRightsSetup> umracRightsSetup = umracRightsSetupRepository.findOneByRoleIdAndActionsId(roleId,moduleId,subModuleId,actionId.toString());

            if(umracRightsSetup != null && umracRightsSetup.size() > 0){
                Status = true;
                tree.addProperty("actionStatus", Status);
            }else{
                tree.addProperty("actionStatus", Status);
            }
        }else {
            tree.addProperty("actionStatus", Status);
        }

        trees.add(tree);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        TreeJson = gson.toJson(trees);

        return TreeJson;
    }
}
