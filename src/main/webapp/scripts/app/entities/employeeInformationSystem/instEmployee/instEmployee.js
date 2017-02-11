'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            /*.state('instEmployee', {
                parent: 'entity',
                url: '/instEmployees',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmployee.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmployee/instEmployees.html',
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
            .state('employeeInfo.personalInfo', {
                parent: 'employeeInfo',
                url: '/personal-info',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instEmployee.detail.title'
                },
                views: {
                    'employeeView@employeeInfo': {
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmployee/instEmployee-detail.html',
                        controller: 'InstEmployeeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmployee');
                        $translatePartialLoader.addPart('empType');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('quota');
                        $translatePartialLoader.addPart('instEmplBankInfo');
                        $translatePartialLoader.addPart('maritalStatus');
                        $translatePartialLoader.addPart('bloodGroup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstEmployee', function($stateParams, InstEmployee) {
                        return InstEmployee.get({id : $stateParams.id});
                    }]
                }
            })
            /*.state('employeeInfo.personalInfo.new', {
                parent: 'employeeInfo.personalInfo',
                url: '/new',
                data: {
                    authorities: [],
                },
                views: {
                    'employeeView@employeeInfo': {
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmployee/instEmployee-dialog.html',
                        controller: 'InstEmployeeDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmployee');
                        $translatePartialLoader.addPart('empType');
                        $translatePartialLoader.addPart('maritalStatus');
                        $translatePartialLoader.addPart('bloodGroup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstEmployee', function($stateParams, InstEmployee) {
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
                    }]
                }
                *//*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmployee/instEmployee-dialog.html',
                        controller: 'InstEmployeeDialogController',
                        size: 'lg',
                        resolve: {
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
                    }).result.then(function(result) {
                        $state.go('instEmployee', null, { reload: true });
                    }, function() {
                        $state.go('instEmployee');
                    })
                }]*//*
            })*/
            .state('employeeInfo.personalInfo.edit', {
                parent: 'employeeInfo.personalInfo',
                url: '/edit',
                data: {
                    authorities: [],
                },
                params: {
                    allInfo:null
                },
                views: {
                    'employeeView@employeeInfo': {
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmployee/instEmployee-dialog.html',
                        controller: 'InstEmployeeDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmployee');
                        $translatePartialLoader.addPart('empType');
                        $translatePartialLoader.addPart('maritalStatus');
                        $translatePartialLoader.addPart('upazila');
                        $translatePartialLoader.addPart('quota');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('instEmplBankInfo');
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('bloodGroup');
                        return $translate.refresh();
                    }]
                }
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmployee/instEmployee-dialog.html',
                        controller: 'InstEmployeeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstEmployee', function(InstEmployee) {
                                return InstEmployee.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmployee', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]*/
            })
            .state('employeeInfo.personalInfo.delete', {
                parent: 'employeeInfo.personalInfo',
                url: '/{id}/delete',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmployee/instEmployee-delete-dialog.html',
                        controller: 'InstEmployeeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstEmployee', function(InstEmployee) {
                                return InstEmployee.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmployee', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            ;
    });
