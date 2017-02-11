'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('vehiclemgt', {
                parent: 'vehicle',
                url: '/vehicles',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.vclVehicle.home.title'
                },
                views: {
                    'vehicleManagementView@vehicle': {
                        templateUrl: 'scripts/app/entities/vehicle/vehicle/vclVehicles.html',
                        controller: 'VclVehicleController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vclVehicle');
                        $translatePartialLoader.addPart('vehicleTypes');
                        $translatePartialLoader.addPart('vehicleStatus');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('vehiclemgt.detail', {
                parent: 'vehiclemgt',
                url: '/vehiclemgt/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.vclVehicle.detail.title'
                },
                views: {
                    'vehicleManagementView@vehicle': {
                        templateUrl: 'scripts/app/entities/vehicle/vehicle/vclVehicle-detail.html',
                        controller: 'VclVehicleDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vclVehicle');
                        $translatePartialLoader.addPart('vehicleTypes');
                        $translatePartialLoader.addPart('vehicleStatus');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'VclVehicle', function($stateParams, VclVehicle) {
                        return VclVehicle.get({id : $stateParams.id});
                    }]
                }
            })
            .state('vehiclemgt.new', {
                parent: 'vehiclemgt',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'vehicleManagementView@vehicle': {
                        templateUrl: 'scripts/app/entities/vehicle/vehicle/vclVehicle-dialog.html',
                        controller: 'VclVehicleDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vclVehicle');
                        $translatePartialLoader.addPart('vehicleTypes');
                        $translatePartialLoader.addPart('vehicleStatus');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            vehicleName: null,
                            vehicleNumber: null,
                            vehicleType: null,
                            licenseNumber: null,
                            chassisNumber: null,
                            dateOfBuying: null,
                            supplierName: null,
                            noOfSeats: null,
                            passengerCapacity: null,
                            vehicleAvailability: null,
                            activeStatus: null,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('vehiclemgt.edit', {
                parent: 'vehiclemgt',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },

                views: {
                    'vehicleManagementView@vehicle': {
                        templateUrl: 'scripts/app/entities/vehicle/vehicle/vclVehicle-dialog.html',
                        controller: 'VclVehicleDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vclVehicle');
                        $translatePartialLoader.addPart('vehicleTypes');
                        $translatePartialLoader.addPart('vehicleStatus');
                        return $translate.refresh();
                    }],
                    entity: ['VclVehicle','$stateParams', function(VclVehicle,$stateParams) {
                        return VclVehicle.get({id : $stateParams.id});
                    }]
                }
            })
            .state('vehiclemgt.delete', {
                parent: 'vehiclemgt',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/vehicle/vehicle/vclVehicle-delete-dialog.html',
                        controller: 'VclVehicleDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['VclVehicle', function(VclVehicle) {
                                return VclVehicle.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('vehiclemgt', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
