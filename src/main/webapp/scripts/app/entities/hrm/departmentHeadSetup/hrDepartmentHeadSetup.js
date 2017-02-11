'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrDepartmentHeadSetup', {
                parent: 'hrm',
                url: '/hrDepartmentHeadSetups',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrDepartmentHeadSetup.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/departmentHeadSetup/hrDepartmentHeadSetups.html',
                        controller: 'HrDepartmentHeadSetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrDepartmentHeadSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrDepartmentHeadSetup.detail', {
                parent: 'hrm',
                url: '/hrDepartmentHeadSetup/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrDepartmentHeadSetup.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/departmentHeadSetup/hrDepartmentHeadSetup-detail.html',
                        controller: 'HrDepartmentHeadSetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrDepartmentHeadSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrDepartmentHeadSetup', function($stateParams, HrDepartmentHeadSetup) {
                        return HrDepartmentHeadSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrDepartmentHeadSetup.new', {
                parent: 'hrDepartmentHeadSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/departmentHeadSetup/hrDepartmentHeadSetup-dialog.html',
                        controller: 'HrDepartmentHeadSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrDepartmentHeadSetup');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            departmentCode: null,
                            departmentName: null,
                            departmentDetail: null,
                            activeStatus: true,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('hrDepartmentHeadSetup.edit', {
                parent: 'hrDepartmentHeadSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/departmentHeadSetup/hrDepartmentHeadSetup-dialog.html',
                        controller: 'HrDepartmentHeadSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrDepartmentHeadSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrDepartmentHeadSetup', function($stateParams, HrDepartmentHeadSetup) {
                        return HrDepartmentHeadSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrDepartmentHeadSetup.delete', {
                parent: 'hrDepartmentHeadSetup',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/departmentHeadSetup/hrDepartmentHeadSetup-delete-dialog.html',
                        controller: 'HrDepartmentHeadSetupDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrDepartmentHeadSetup', function(HrDepartmentHeadSetup) {
                                return HrDepartmentHeadSetup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrDepartmentHeadSetup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
