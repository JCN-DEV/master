'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('vclVehicleDriverAssign', {
                parent: 'vehicle',
                url: '/vclVehicleDriverAssigns',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.vclVehicleDriverAssign.home.title'
                },
                views: {
                    'vehicleManagementView@vehicle': {
                        templateUrl: 'scripts/app/entities/vehicle/driverAssign/vclVehicleDriverAssigns.html',
                        controller: 'VclVehicleDriverAssignController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vclVehicleDriverAssign');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('vclVehicleDriverAssign.detail', {
                parent: 'vehicle',
                url: '/vclVehicleDriverAssign/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.vclVehicleDriverAssign.detail.title'
                },
                views: {
                    'vehicleManagementView@vehicle': {
                        templateUrl: 'scripts/app/entities/vehicle/driverAssign/vclVehicleDriverAssign-detail.html',
                        controller: 'VclVehicleDriverAssignDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vclVehicleDriverAssign');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'VclVehicleDriverAssign', function($stateParams, VclVehicleDriverAssign) {
                        return VclVehicleDriverAssign.get({id : $stateParams.id});
                    }]
                }
            })
            .state('vclVehicleDriverAssign.new', {
                parent: 'vclVehicleDriverAssign',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'vehicleManagementView@vehicle': {
                        templateUrl: 'scripts/app/entities/vehicle/driverAssign/vclVehicleDriverAssign-dialog.html',
                        controller: 'VclVehicleDriverAssignDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vclVehicleDriverAssign');
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
            .state('vclVehicleDriverAssign.edit', {
                parent: 'vclVehicleDriverAssign',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },

                views: {
                    'vehicleManagementView@vehicle': {
                        templateUrl: 'scripts/app/entities/vehicle/driverAssign/vclVehicleDriverAssign-dialog.html',
                        controller: 'VclVehicleDriverAssignDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vclVehicleDriverAssign');
                        return $translate.refresh();
                    }],
                    entity: ['VclVehicleDriverAssign','$stateParams', function(VclVehicleDriverAssign,$stateParams) {
                        return VclVehicleDriverAssign.get({id : $stateParams.id});
                    }]
                }
            })
            .state('vclVehicleDriverAssign.delete', {
                parent: 'vclVehicleDriverAssign',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/vehicle/driverAssign/vclVehicleDriverAssign-delete-dialog.html',
                        controller: 'VclVehicleDriverAssignDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['VclVehicleDriverAssign', function(VclVehicleDriverAssign) {
                                return VclVehicleDriverAssign.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('vclVehicleDriverAssign', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
