'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('vclEmployeeAssign', {
                parent: 'vehicle',
                url: '/vclEmployeeAssigns',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.vclEmployeeAssign.home.title'
                },
                views: {
                    'vehicleManagementView@vehicle': {
                        templateUrl: 'scripts/app/entities/vehicle/employeeAssign/vclEmployeeAssigns.html',
                        controller: 'VclEmployeeAssignController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vclEmployeeAssign');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('vclEmployeeAssign.detail', {
                parent: 'vehicle',
                url: '/vclEmployeeAssign/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.vclEmployeeAssign.detail.title'
                },
                views: {
                    'vehicleManagementView@vehicle': {
                        templateUrl: 'scripts/app/entities/vehicle/employeeAssign/vclEmployeeAssign-detail.html',
                        controller: 'VclEmployeeAssignDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vclEmployeeAssign');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'VclEmployeeAssign', function($stateParams, VclEmployeeAssign) {
                        return VclEmployeeAssign.get({id : $stateParams.id});
                    }]
                }
            })
            .state('vclEmployeeAssign.new', {
                parent: 'vclEmployeeAssign',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'vehicleManagementView@vehicle': {
                        templateUrl: 'scripts/app/entities/vehicle/employeeAssign/vclEmployeeAssign-dialog.html',
                        controller: 'VclEmployeeAssignDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vclEmployeeAssign');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('vclEmployeeAssign.edit', {
                parent: 'vclEmployeeAssign',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },

                views: {
                    'vehicleManagementView@vehicle': {
                        templateUrl: 'scripts/app/entities/vehicle/employeeAssign/vclEmployeeAssign-dialog.html',
                        controller: 'VclEmployeeAssignDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vclEmployeeAssign');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'VclEmployeeAssign', function($stateParams, VclEmployeeAssign) {
                        return VclEmployeeAssign.get({id : $stateParams.id});
                    }]
                }
            })
            .state('vclEmployeeAssign.delete', {
                parent: 'vclEmployeeAssign',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/vehicle/employeeAssign/vclEmployeeAssign-delete-dialog.html',
                        controller: 'VclEmployeeAssignDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['VclEmployeeAssign', function(VclEmployeeAssign) {
                                return VclEmployeeAssign.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('vclEmployeeAssign', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
