'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('driver', {
                parent: 'vehicle',
                url: '/drivers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.vclDriver.home.title'
                },
                views: {
                    'vehicleManagementView@vehicle': {
                        templateUrl: 'scripts/app/entities/vehicle/driver/vclDrivers.html',
                        controller: 'VclDriverController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vclDriver');
                        $translatePartialLoader.addPart('bloodGroup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('driver.detail', {
                parent: 'driver',
                url: '/driver/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.vclDriver.detail.title'
                },
                views: {
                    'vehicleManagementView@vehicle': {
                        templateUrl: 'scripts/app/entities/vehicle/driver/vclDriver-detail.html',
                        controller: 'VclDriverDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vclDriver');
                        $translatePartialLoader.addPart('bloodGroup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'VclDriver', function($stateParams, VclDriver) {
                        return VclDriver.get({id : $stateParams.id});
                    }]
                }
            })
            .state('driver.new', {
                parent: 'driver',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'vehicleManagementView@vehicle': {
                        templateUrl: 'scripts/app/entities/vehicle/driver/vclDriver-dialog.html',
                        controller: 'VclDriverDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vclDriver');
                        $translatePartialLoader.addPart('bloodGroup');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            driverName: null,
                            licenseNumber: null,
                            presentAddress: null,
                            permanentAddress: null,
                            mobileNumber: null,
                            emergencyNumber: null,
                            joinDate: null,
                            retirementDate: null,
                            photoName: null,
                            driverPhoto: null,
                            driverPhotoContentType: null,
                            activeStatus:null,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('driver.edit', {
                parent: 'driver',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },

                views: {
                    'vehicleManagementView@vehicle': {
                        templateUrl: 'scripts/app/entities/vehicle/driver/vclDriver-dialog.html',
                        controller: 'VclDriverDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vclDriver');
                        $translatePartialLoader.addPart('bloodGroup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'VclDriver', function($stateParams, VclDriver) {
                        return VclDriver.get({id : $stateParams.id});
                    }]
                }
            })
            .state('driver.delete', {
                parent: 'driver',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/vehicle/driver/vclDriver-delete-dialog.html',
                        controller: 'VclDriverDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['VclDriver', function(VclDriver) {
                                return VclDriver.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('driver', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
