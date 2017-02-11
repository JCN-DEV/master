'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            /*.state('instEmpEduQuali', {
                parent: 'entity',
                url: '/instEmpEduQualis',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmpEduQuali.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmpEduQuali/instEmpEduQualis.html',
                        controller: 'InstEmpEduQualiController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmpEduQuali');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })*/
            .state('employeeInfo.educationalHistory', {
                parent: 'employeeInfo',
                url: '/educational-history',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instEmpEduQuali.detail.title'
                },
                views: {
                    'employeeView@employeeInfo': {
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmpEduQuali/instEmpEduQuali-detail.html',
                        controller: 'InstEmpEduQualiDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmpEduQuali');
                        return $translate.refresh();
                    }]
                }
            })
            .state('employeeInfo.educationalHistory.new', {
                parent: 'employeeInfo.educationalHistory',
                url: '/new',
                data: {
                    authorities: []
                },
                params: {
                    instEmpEduQuali:null
                },
                views:{
                    'employeeView@employeeInfo':{
                            templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmpEduQuali/instEmpEduQuali-dialog.html',
                            controller: 'InstEmpEduQualiDialogController'
                    }
                },
                resolve: {
                    entity: function () {
                        return [{
                            isGpaResult: true,
                            fromUniversity: false,
                            certificateName: null,
                            board: null,
                            session: null,
                            semester: null,
                            rollNo: null,
                            passingYear: null,
                            cgpa: null,
                            certificateCopy: null,
                            certificateCopyContentType: null,
                            status: null,
                            id: null,
                            instEmployee: null
                       }];
                    }
                }
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmpEduQuali/instEmpEduQuali-dialog.html',
                        controller: 'InstEmpEduQualiDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    certificateName: null,
                                    board: null,
                                    session: null,
                                    semester: null,
                                    rollNo: null,
                                    passingYear: null,
                                    cgpa: null,
                                    certificateCopy: null,
                                    certificateCopyContentType: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instEmpEduQuali', null, { reload: true });
                    }, function() {
                        $state.go('instEmpEduQuali');
                    })
                }]*/
            })
            .state('employeeInfo.educationalHistory.edit', {
                parent: 'employeeInfo.educationalHistory',
                url: '/edit',
                data: {
                    authorities: ['ROLE_INSTEMP'],
                },
                params: {
                    instEmpEduQuali:null
                },
                views:{
                    'employeeView@employeeInfo':{
                            templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmpEduQuali/instEmpEduQuali-dialog.html',
                            controller: 'InstEmpEduQualiDialogController'
                    }
                },
                resolve: {
                    entity: ['InstEmpEduQuali','$stateParams', function(InstEmpEduQuali,$stateParams) {
                        return null;
                    }]
                }
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmpEduQuali/instEmpEduQuali-dialog.html',
                        controller: 'InstEmpEduQualiDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstEmpEduQuali', function(InstEmpEduQuali) {
                                return InstEmpEduQuali.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmpEduQuali', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]*/
            })
            .state('employeeInfo.educationalHistory.delete', {
                parent: 'employeeInfo.educationalHistory',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmpEduQuali/instEmpEduQuali-delete-dialog.html',
                        controller: 'InstEmpEduQualiDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstEmpEduQuali', function(InstEmpEduQuali) {
                                return InstEmpEduQuali.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmpEduQuali', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
