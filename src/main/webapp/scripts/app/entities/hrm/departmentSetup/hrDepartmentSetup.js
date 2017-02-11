'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrDepartmentSetup', {
                parent: 'hrm',
                url: '/hrDepartmentSetups',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrDepartmentSetup.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/departmentSetup/hrDepartmentSetups.html',
                        controller: 'HrDepartmentSetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrDepartmentSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrDepartmentSetup.detail', {
                parent: 'hrm',
                url: '/hrDepartmentSetup/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrDepartmentSetup.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/departmentSetup/hrDepartmentSetup-detail.html',
                        controller: 'HrDepartmentSetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrDepartmentSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrDepartmentSetup', function($stateParams, HrDepartmentSetup) {
                        return HrDepartmentSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrDepartmentSetup.new', {
                parent: 'hrDepartmentSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/departmentSetup/hrDepartmentSetup-dialog.html',
                        controller: 'HrDepartmentSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrDepartmentSetup');
                        $translatePartialLoader.addPart('hrDepartmentHeadInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
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
            .state('hrDepartmentSetup.edit', {
                parent: 'hrDepartmentSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/departmentSetup/hrDepartmentSetup-dialog.html',
                        controller: 'HrDepartmentSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrDepartmentSetup');
                        $translatePartialLoader.addPart('hrDepartmentHeadInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrDepartmentSetup', function($stateParams, HrDepartmentSetup) {
                        return HrDepartmentSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrDepartmentSetup.delete', {
                parent: 'hrDepartmentSetup',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/departmentSetup/hrDepartmentSetup-delete-dialog.html',
                        controller: 'HrDepartmentSetupDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrDepartmentSetup', function(HrDepartmentSetup) {
                                return HrDepartmentSetup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrDepartmentSetup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
