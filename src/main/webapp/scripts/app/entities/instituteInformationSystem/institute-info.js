'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instituteInfo', {
                parent: 'entity',
                url: '/institute-info',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instGenInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/institute-info.html',
                        controller: 'InstituteInfoController'
                    },
                    'instituteView@instituteInfo':{
                          templateUrl: 'scripts/app/entities/instituteInformationSystem/institute-dashboard.html',
                          controller: 'InstituteDashboardController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('instituteInfo');
                        $translatePartialLoader.addPart('instType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    instituteList: function(){
                        return null;
                    }
                }
            })   .state('setup', {
                parent: 'entity',
                url: '/Setup',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instGenInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/setup-info.html',
                        controller: 'InstituteInfoController'
                    },
                    'setupView@setup':{
                          templateUrl: 'scripts/app/entities/instituteInformationSystem/institute-dashboard.html',
                          controller: 'InstituteDashboardController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('instType');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('instituteInfo');
                        return $translate.refresh();
                    }],
                    instituteList: function(){
                        return null;
                    }
                }
            })
            .state('instituteInfo.dashboard',{
                parent: 'instituteInfo',
                url: '/dashboard',
                data: {
                    authorities: []
                },
                views: {
                    'instituteView@instituteInfo':{
                          templateUrl: 'scripts/app/entities/instituteInformationSystem/institute-dashboard.html',
                          controller: 'InstituteDashboardController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instituteInfo');
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('instType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instituteInfo.home',{
                parent: 'instituteInfo',
                url: '/home',
                data: {
                    authorities: []
                },
                views: {
                    'content@':{
                          templateUrl: 'scripts/app/entities/instituteInformationSystem/institute-home.html',
                          controller: 'InstituteDashboardController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instituteInfo');
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('instType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instituteInfo.report',{
                 parent: 'instituteInfo',
                 url: '/report',
                 data: {
                     authorities: []
                 },
                 views: {
                     'instituteView@instituteInfo':{
                           templateUrl: 'scripts/app/entities/instituteInformationSystem/institute-dashboard.html',
                           controller: 'InstituteDashboardController'
                     }
                 },
                 resolve: {
                     translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                         $translatePartialLoader.addPart('instituteInfo');
                         $translatePartialLoader.addPart('instGenInfo');
                         $translatePartialLoader.addPart('instType');
                         $translatePartialLoader.addPart('global');
                         return $translate.refresh();
                     }]
                 }
            })
            .state('instituteInfo.instituteList',{
                  parent: 'instituteInfo',
                  url: '/institute-list',
                  data: {
                     authorities: []
                  },
                  views: {
                     'instituteView@instituteInfo':{
                           templateUrl: 'scripts/app/entities/instituteInformationSystem/institute-list.html',
                           controller: 'InstituteInfoController'
                     }
                  },
                  resolve: {
                     translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                         $translatePartialLoader.addPart('instGenInfo');
                         $translatePartialLoader.addPart('instType');
                         $translatePartialLoader.addPart('global');
                         return $translate.refresh();
                     }],
                      instituteList:function (){
                        return  1;
                      }
                  }
            }).state('instituteInfo.mpoEnlistedInst',{
                  parent: 'instituteInfo',
                  url: '/mpo-listed-institute',
                  data: {
                     authorities: ['ROLE_ADMIN']
                  },
                  views: {
                     'instituteView@instituteInfo':{
                           templateUrl: 'scripts/app/entities/instituteInformationSystem/institute-mpo-listed.html',
                         //InstituteMpoListController is located to >>>>>>   /home/leads/projects/step/src/main/webapp/scripts/app/entities/instituteInformationSystem/institute-info.controller.js
                         controller: 'InstituteMpoListController'
                     }
                  },
                  resolve: {
                     translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                         $translatePartialLoader.addPart('instGenInfo');
                         $translatePartialLoader.addPart('instType');
                         $translatePartialLoader.addPart('global');
                         return $translate.refresh();
                     }],
                      instituteList:function (){
                        return  1;
                      }
                  }
            }).state('instituteInfo.mpoEnlistedCourse',{
                  parent: 'instituteInfo',
                  url: '/mpo-listed-courses',
                  data: {
                     authorities: ['ROLE_ADMIN']
                  },
                  views: {
                     'instituteView@instituteInfo':{
                           templateUrl: 'scripts/app/entities/instituteInformationSystem/course-list-mpo-listed.html',
                         controller: 'CourseMpoListController'
                     }
                  },
                  resolve: {
                     translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                         $translatePartialLoader.addPart('iisCourseInfo');
                         $translatePartialLoader.addPart('instType');
                         $translatePartialLoader.addPart('global');
                         return $translate.refresh();
                     }],
                      instituteList:function (){
                        return  1;
                      }
                  }
            }).state('instituteInfo.mpoEnlistedCurriculum',{
                  parent: 'instituteInfo',
                  url: '/mpo-listed-curriculums',
                  data: {
                     authorities: ['ROLE_ADMIN']
                  },
                  views: {
                     'instituteView@instituteInfo':{
                           templateUrl: 'scripts/app/entities/instituteInformationSystem/curriculum-list-mpo-listed.html',
                         controller: 'CurriculumMpoListController'
                     }
                  },
                  resolve: {
                     translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                         $translatePartialLoader.addPart('iisCurriculumInfo');
                         $translatePartialLoader.addPart('instType');
                         $translatePartialLoader.addPart('global');
                         return $translate.refresh();
                     }],
                      instituteList:function (){
                        return  1;
                      }
                  }
            }).state('instituteInfo.institutesWithLevel',{
                  parent: 'instituteInfo',
                  url: '/leve-wise-institute/:levelName',
                  data: {
                     authorities: ['ROLE_ADMIN']
                  },
                  views: {
                     'instituteView@instituteInfo':{
                           templateUrl: 'scripts/app/entities/instituteInformationSystem/level-wise-institutes.html',
                         //LevelWiseInstitutesController is located to >>>>>>   /home/leads/projects/step/src/main/webapp/scripts/app/entities/instituteInformationSystem/institute-info.controller.js
                         controller: 'LevelWiseInstitutesController'
                     }
                  },
                  resolve: {
                     translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                         $translatePartialLoader.addPart('instGenInfo');
                         $translatePartialLoader.addPart('instType');
                         $translatePartialLoader.addPart('global');
                         return $translate.refresh();
                     }],
                      instituteList:function (){
                        return  1;
                      }
                  }
            }).state('instituteInfo.editMpoCode', {
                parent: 'instituteInfo.mpoEnlistedInst',
                url: '/update-mpo-code/:id',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/inst-code-edit-dialog.html',
                        controller: 'InstMpoCodeChangeDialogController',
                        size: 'md'
                    })
                }]
            }).state('instituteInfo.editCourseMpoDate', {
                parent: 'instituteInfo.mpoEnlistedCourse',
                url: '/edit-mpo-date/:id',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/course-mpo-date-edit-dialog.html',
                        controller: 'CourseMpoDateChangeDialogController',
                        size: 'md'
                    })
                }]
            }).state('instituteInfo.editCurriculumMpoDate', {
                parent: 'instituteInfo.mpoEnlistedCurriculum',
                url: '/edit-curriculum-date/:id',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/curriculum-mpo-date-edit-dialog.html',
                        controller: 'CurriculumMpoDateChangeDialogController',
                        size: 'md'
                    })
                }]
            })
            .state('instituteInfo.pendingInstituteList',{
                  parent: 'instituteInfo',
                  url: '/pending-institute-list',
                  data: {
                     authorities: []
                  },
                  views: {
                     'instituteView@instituteInfo':{
                           templateUrl: 'scripts/app/entities/instituteInformationSystem/institute-list.html',
                           controller: 'InstituteInfoController'
                     }
                  },
                  resolve: {
                     translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                         $translatePartialLoader.addPart('instGenInfo');
                         $translatePartialLoader.addPart('instType');
                         $translatePartialLoader.addPart('global');
                         return $translate.refresh();
                     }],
                     instituteList: function ( ){
                          return  0;
                     }
                  }
            }).state('instituteInfo.updateInfoPendingInstituteList',{
                  parent: 'instituteInfo',
                  url: '/pending-info-institute-list',
                  data: {
                     authorities: []
                  },
                  views: {
                     'instituteView@instituteInfo':{
                           templateUrl: 'scripts/app/entities/instituteInformationSystem/institute-list.html',
                           controller: 'InstitutePendingInfoController'
                     }
                  },
                  resolve: {
                     translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                         $translatePartialLoader.addPart('instGenInfo');
                         $translatePartialLoader.addPart('instType');
                         $translatePartialLoader.addPart('global');
                         return $translate.refresh();
                     }],
                     instituteList: function ( ){
                          return  0;
                     }
                  }
            }).state('instituteInfo.rejectedInstituteList',{
                  parent: 'instituteInfo',
                  url: '/rejected-institute-list',
                  data: {
                     authorities: []
                  },
                  views: {
                     'instituteView@instituteInfo':{
                           templateUrl: 'scripts/app/entities/instituteInformationSystem/institute-list.html',
                           controller: 'InstituteInfoRejectedListController'
                     }
                  },
                  resolve: {
                     translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                         $translatePartialLoader.addPart('instGenInfo');
                         $translatePartialLoader.addPart('instType');
                         $translatePartialLoader.addPart('global');
                         return $translate.refresh();
                     }],
                     instituteList: function ( ){
                          return  0;
                     }
                  }
            }).state('instituteInfo.jpOfficer',{
                  parent: 'instituteInfo',
                  url: '/job-placement-officer',
                  data: {
                     authorities: []
                  },
                  views: {
                     'instituteView@instituteInfo':{
                           templateUrl: 'scripts/app/entities/instituteInformationSystem/inst-jpadmins.html',
                           controller: 'InstituteInfoJobPlacementOfficerController'
                     }
                  },
                  resolve: {
                     translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                         $translatePartialLoader.addPart('instEmployee');
                         $translatePartialLoader.addPart('instType');
                         $translatePartialLoader.addPart('global');
                         return $translate.refresh();
                     }],
                     instituteList: function ( ){
                          return  0;
                     }
                  }
            })
            .state('instituteInfo.approve', {
                 parent: 'instituteInfo',
                 url: '/approve/{id}',
                 data: {
                     /*authorities: ['ROLE_ADMIN'],
                     authorities: [],*/
                 },
                 views: {
                     'instituteView@instituteInfo':{
                          templateUrl: 'scripts/app/entities/instituteInformationSystem/institute-approve-panel.html',
                          controller: 'InstituteInfoApproveController'
                     }
                 },
                 resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('iisCurriculumInfo');
                        $translatePartialLoader.addPart('iisCourseInfo');
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('instAdmInfo');
                        $translatePartialLoader.addPart('insAcademicInfo');
                        $translatePartialLoader.addPart('instFinancialInfo');
                        $translatePartialLoader.addPart('instLabInfo');
                        $translatePartialLoader.addPart('instShopInfo');
                        $translatePartialLoader.addPart('instPlayGroundInfo');
                        $translatePartialLoader.addPart('instInfraInfo');
                        $translatePartialLoader.addPart('instEmpCount');
                        $translatePartialLoader.addPart('instGovernBody');
                        $translatePartialLoader.addPart('instType');
                        $translatePartialLoader.addPart('curriculum');
                        $translatePartialLoader.addPart('accountType');
                        $translatePartialLoader.addPart('instBuilding');
                        $translatePartialLoader.addPart('instLand');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]

                 }
            })
            .state('instituteInfo.view', {
                parent: 'instituteInfo',
                url: '/view/{id}',
                data: {
                    /*authorities: ['ROLE_ADMIN'],
                     authorities: [],*/
                },
                views: {
                    'instituteView@instituteInfo':{
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/institute-approve-panel.html',
                        controller: 'InstituteInfoApproveController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('instAdmInfo');
                        $translatePartialLoader.addPart('insAcademicInfo');
                        $translatePartialLoader.addPart('instFinancialInfo');
                        $translatePartialLoader.addPart('instInfraInfo');
                        $translatePartialLoader.addPart('instEmpCount');
                        $translatePartialLoader.addPart('instLabInfo');
                        $translatePartialLoader.addPart('instShopInfo');
                        $translatePartialLoader.addPart('instPlayGroundInfo');
                        $translatePartialLoader.addPart('instInfraInfo');
                        $translatePartialLoader.addPart('instType');
                        $translatePartialLoader.addPart('curriculum');
                        $translatePartialLoader.addPart('accountType');
                        $translatePartialLoader.addPart('instBuilding');
                        $translatePartialLoader.addPart('instLand');
                        $translatePartialLoader.addPart('iisCurriculumInfo');
                        $translatePartialLoader.addPart('iisCourseInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]

                }
            })
            /*.state('instituteInfo.addEmployee', {
                parent: 'employeeInfo',
                url: '/add-employee',
                data: {
                    authorities: ['ROLE_INSTITUTE'],
                    pageTitle: 'stepApp.instEmployee.home.title'
                },
                views: {
                    'employeeView@employeeInfo': {
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/employee-registration.html',
                        controller: 'InstEmployeeRegistrationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmployee');
                        $translatePartialLoader.addPart('empType');
                        $translatePartialLoader.addPart('maritalStatus');
                        $translatePartialLoader.addPart('bloodGroup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            name: null,
                            email: null,
                            contactNo: null,
                            fatherName: null,
                            motherName: null,
                            dob: null,
                            category: null,
                            gender: null,
                            maritalStatus: null,
                            bloodGroup: null,
                            tin: null,
                            image: null,
                            imageContentType: null,
                            nationality: null,
                            nid: null,
                            nidImage: null,
                            nidImageContentType: null,
                            birthCertNo: null,
                            birthCertImage: null,
                            birthCertImageContentType: null,
                            conPerName: null,
                            conPerMobile: null,
                            conPerAddress: null,
                            id: null
                        };
                    }
                }
            })
            .state('instituteInfo.employeeList', {
                parent: 'instituteInfo',
                url: '/employee-list',
                data: {
                    authorities: ['ROLE_INSTITUTE','ROLE_ADMIN'],
                    pageTitle: 'stepApp.instEmployee.home.title'
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmployee/instEmployees.html',
                        controller: 'InstEmployeeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmployee');
                        $translatePartialLoader.addPart('empType');
                        $translatePartialLoader.addPart('maritalStatus');
                        $translatePartialLoader.addPart('bloodGroup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instituteInfo.PendingEmployeeList', {
                parent: 'instituteInfo',
                url: '/pending-employee-list',
                data: {
                    authorities: ['ROLE_INSTITUTE','ROLE_ADMIN'],
                    pageTitle: 'stepApp.instEmployee.home.title'
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmployee/instEmployeesPending.html',
                        controller: 'InstEmployeeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmployee');
                        $translatePartialLoader.addPart('empType');
                        $translatePartialLoader.addPart('maritalStatus');
                        $translatePartialLoader.addPart('bloodGroup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })*/





    });
