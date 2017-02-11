'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('employeeInfo', {
                parent: 'entity',
                url: '/employee-info',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instEmployee.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/employee-info.html',
                        controller: 'EmployeeInfoController'
                    },
                    'employeeView@employeeInfo':{
                         templateUrl: 'scripts/app/entities/employeeInformationSystem/employee-dashboard.html',
                         controller: 'EmployeeDashboardController'
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
            .state('employeeInfo.dashboard', {
                parent: 'employeeInfo',
                url: '/employee-dashboard',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instEmployee.home.title'
                },
                views: {
                    'employeeView@employeeInfo':{
                         templateUrl: 'scripts/app/entities/employeeInformationSystem/employee-dashboard.html',
                         controller: 'EmployeeDashboardController'
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

            .state('employeeInfo.home', {
                parent: 'employeeInfo',
                url: '/employee-home',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instEmployee.home.title'
                },
                views: {
                    'content@':{
                         templateUrl: 'scripts/app/entities/employeeInformationSystem/employee-home.html',
                         controller: 'EmployeeDashboardController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('employeeInfo.imageUpload', {
                parent: 'employeeInfo',
                url: '/image',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instEmployee.home.title'
                },
                views: {
                    'employeeView@employeeInfo':{
                         templateUrl: 'scripts/app/entities/employeeInformationSystem/employeeUploadedFiles/employee-image-upload.html',
                         controller: 'EmployeeImageUploadController'
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



            .state('employeeInfo.nationalityAndBirthCertificate', {
                parent: 'employeeInfo',
                url: '/nationality',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instEmployee.home.title'
                },
                views: {
                    'employeeView@employeeInfo':{
                         templateUrl: 'scripts/app/entities/employeeInformationSystem/employeeUploadedFiles/nationality-birthCertificateDetail.html',
                         controller: 'EmployeeBirthCertificateController'
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
            .state('employeeInfo.nationalityAndBirthCertificate.edit', {
                parent: 'employeeInfo.nationalityAndBirthCertificate',
                url: '/edit',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instEmployee.home.title'
                },
                views: {
                    'employeeView@employeeInfo':{
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/employeeUploadedFiles/nationality-birthCertificate.html',
                        controller: 'EmployeeBirthCertificateController'
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
            .state('employeeInfo.quotaCertificate', {
                parent: 'employeeInfo',
                url: '/quota',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instEmployee.home.title'
                },
                views: {
                    'employeeView@employeeInfo':{
                         templateUrl: 'scripts/app/entities/employeeInformationSystem/employeeUploadedFiles/quota-certificateDetail.html',
                         controller: 'EmployeeDashboardController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmployee');
                        $translatePartialLoader.addPart('empType');
                        $translatePartialLoader.addPart('maritalStatus');
                        $translatePartialLoader.addPart('quota');
                        $translatePartialLoader.addPart('bloodGroup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })

            .state('employeeInfo.report', {
                parent: 'employeeInfo',
                url: '/employee-report',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instEmployee.home.title'
                },
                views: {
                    'employeeView@employeeInfo':{
                         templateUrl: 'scripts/app/entities/employeeInformationSystem/employee-dashboard.html',
                         controller: 'EmployeeDashboardController'
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
            .state('instituteInfo.addEmployee', {
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
                        $translatePartialLoader.addPart('jobQuota');
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
                parent: 'employeeInfo',
                url: '/employee-list',
                data: {
                    authorities: ['ROLE_INSTITUTE','ROLE_ADMIN'],
                    pageTitle: 'stepApp.instEmployee.home.title'
                },
                views: {
                    'employeeView@employeeInfo': {
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmployee/instEmployees.html',
                        controller: 'InstEmployeeListController'
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
            }).state('instituteInfo.mpoEnlistedEmployeeList', {
                parent: 'employeeInfo',
                url: '/mpo-enlisted-employee-list',
                data: {
                    authorities: ['ROLE_INSTITUTE','ROLE_ADMIN'],
                    pageTitle: 'stepApp.instEmployee.home.title'
                },
                views: {
                    'employeeView@employeeInfo': {
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmployee/instEmployees.html',
                        controller: 'InstEmployeeMpoEnlistedListController'
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

            .state('instituteInfo.staffList', {
                parent: 'employeeInfo',
                url: '/staff-list',
                data: {
                    authorities: ['ROLE_INSTITUTE','ROLE_ADMIN'],
                    pageTitle: 'stepApp.instEmployee.home.title'
                },
                views: {
                    'employeeView@employeeInfo': {
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmployee/instStaffs.html',
                        controller: 'InstStaffListController'
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
                parent: 'employeeInfo',
                url: '/pending-employee-list',
                data: {
                    authorities: ['ROLE_INSTITUTE','ROLE_ADMIN'],
                    pageTitle: 'stepApp.instEmployee.home.title'
                },
                views: {
                    'employeeView@employeeInfo': {
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmployee/instEmployeesPending.html',
                        controller: 'InstEmployeePendingListController'
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
            .state('instituteInfo.declinedEmployeeList', {
                parent: 'employeeInfo',
                url: '/declined-employee-list',
                data: {
                    authorities: ['ROLE_INSTITUTE','ROLE_ADMIN'],
                    pageTitle: 'stepApp.instEmployee.home.title'
                },
                views: {
                    'employeeView@employeeInfo': {
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmployee/instEmployeesPending.html',
                        controller: 'InstEmployeeDeclinedListController'
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
            .state('employeeInfo.approve', {
                parent: 'employeeInfo',
                url: '/approve/{code}',
                data: {authorities: ['ROLE_INSTITUTE']},
                views: {
                    'employeeView@employeeInfo':{
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/inst-employee-approve-panel.html',
                        controller: 'InstEmployeeInfoApproveController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmployee');
                        $translatePartialLoader.addPart('empType');
                        $translatePartialLoader.addPart('maritalStatus');
                        $translatePartialLoader.addPart('bloodGroup');
                        $translatePartialLoader.addPart('instEmpEduQuali');
                        $translatePartialLoader.addPart('instEmpAddress');
                        $translatePartialLoader.addPart('instEmplBankInfo');
                        $translatePartialLoader.addPart('instEmplExperience');
                        $translatePartialLoader.addPart('instEmplRecruitInfo');
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('instEmplTraining');
                        $translatePartialLoader.addPart('instEmpSpouseInfo');
                        $translatePartialLoader.addPart('relationType');
                        $translatePartialLoader.addPart('instEmployee');
                        $translatePartialLoader.addPart('maritalStatus');
                        $translatePartialLoader.addPart('bloodGroup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]

                }
            })
            .state('employeeInfo.confirm', {
                parent: 'employeeInfo.approve',
                url: '/{id}/approve',
                data: {authorities: ['ROLE_INSTITUTE']},
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/employee-confirm-approve.html',
                        controller: 'InstEmployeeConfirmApproveController',
                        size: 'md',
                        resolve: {
                            entity: ['InstEmployee', function(InstEmployee) {
                                return InstEmployee.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                            $state.go('instituteInfo.PendingEmployeeList',{},{reload:true});
                        }, function() {
                            $state.go('instituteInfo.PendingEmployeeList',{},{reload:true});
                        })
                }]
            })
            .state('employeeInfo.decline', {
                parent: 'employeeInfo.approve',
                url: '/{id}/decline',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/employee-decline-approve.html',
                        controller: 'InstEmployeeDeclineController',
                        size: 'md',
                        resolve: {
                            entity: ['InstEmployee', function(InstEmployee) {
                                return InstEmployee.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                            $state.go('employeeInfo.approve', null, { reload: true });
                        }, function() {
                            $state.go('employeeInfo.approve', null, { reload: true });
                        })
                }]
            })
            .state('employeeInfo.eligible', {
                parent: 'employeeInfo.approve',
                url: '/{id}/eligible',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/employee-eligible.html',
                        controller: 'InstEmployeeEligibleController',
                        size: 'md',
                        resolve: {
                            entity: ['InstEmployee', function(InstEmployee) {
                                return InstEmployee.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                            $state.go('employeeInfo.approve', null, { reload: true });
                        }, function() {
                            $state.go('employeeInfo.approve', null, { reload: true });
                        })
                }]
            });

    });
