'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('vclRequisition', {
                parent: 'vehicle',
                url: '/vclRequisitions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.vclRequisition.home.title'
                },
                views: {
                    'vehicleManagementView@vehicle': {
                        templateUrl: 'scripts/app/entities/vehicle/requisition/vclRequisitions.html',
                        controller: 'VclRequisitionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vclRequisition');
                        $translatePartialLoader.addPart('requisitionTypes');
                        $translatePartialLoader.addPart('vehicleTypes');
                        $translatePartialLoader.addPart('requisitionStatus');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('vclRequisition.detail', {
                parent: 'vehicle',
                url: '/vclRequisition/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.vclRequisition.detail.title'
                },
                views: {
                    'vehicleManagementView@vehicle': {
                        templateUrl: 'scripts/app/entities/vehicle/requisition/vclRequisition-detail.html',
                        controller: 'VclRequisitionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vclRequisition');
                        $translatePartialLoader.addPart('requisitionTypes');
                        $translatePartialLoader.addPart('vehicleTypes');
                        $translatePartialLoader.addPart('requisitionStatus');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'VclRequisition', function($stateParams, VclRequisition) {
                        return VclRequisition.get({id : $stateParams.id});
                    }]
                }
            })
            .state('vclRequisition.new', {
                parent: 'vclRequisition',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'vehicleManagementView@vehicle': {
                        templateUrl: 'scripts/app/entities/vehicle/requisition/vclRequisition-dialog.html',
                        controller: 'VclRequisitionDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vclRequisition');
                        $translatePartialLoader.addPart('requisitionTypes');
                        $translatePartialLoader.addPart('vehicleTypes');
                        $translatePartialLoader.addPart('requisitionStatus');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            requisitionType: null,
                            vehicleType: null,
                            sourceLocation: null,
                            destinationLocation: null,
                            expectedDate: null,
                            returnDate: null,
                            requisitionCause: null,
                            requisitionStatus: null,
                            meterReading: null,
                            fuelReading: null,
                            billAmount: null,
                            expectedArrivalDate: null,
                            comments: null,
                            actionDate: null,
                            actionBy: null,
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
            .state('vclRequisition.edit', {
                parent: 'vclRequisition',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },

                views: {
                    'vehicleManagementView@vehicle': {
                        templateUrl: 'scripts/app/entities/vehicle/requisition/vclRequisition-dialog.html',
                        controller: 'VclRequisitionDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vclRequisition');
                        $translatePartialLoader.addPart('requisitionTypes');
                        $translatePartialLoader.addPart('vehicleTypes');
                        $translatePartialLoader.addPart('requisitionStatus');
                        return $translate.refresh();
                    }],
                    entity: ['VclRequisition','$stateParams', function(VclRequisition,$stateParams) {
                        return VclRequisition.get({id : $stateParams.id});
                    }]
                }
            })
            .state('vclRequisition.delete', {
                parent: 'vclRequisition',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/vehicle/requisition/vclRequisition-delete-dialog.html',
                        controller: 'VclRequisitionDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['VclRequisition', function(VclRequisition) {
                                return VclRequisition.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('vclRequisition', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('vclRequisition.dashboard', {
                parent: 'vehicle',
                url: '/reqdashboard',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.vclRequisition.home.title'
                },
                views: {
                    'vehicleManagementView@vehicle': {
                        templateUrl: 'scripts/app/entities/vehicle/requisition/vclRequisitions-dashboard.html',
                        controller: 'VclRequisitionDashboardController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vclRequisition');
                        $translatePartialLoader.addPart('requisitionTypes');
                        $translatePartialLoader.addPart('vehicleTypes');
                        $translatePartialLoader.addPart('requisitionStatus');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('vclRequisition.reject', {
                parent: 'vclRequisition',
                url: '/{id}/reject',
                data: {
                    authorities: ['ROLE_USER'],
                },

                views: {
                    'vehicleManagementView@vehicle': {
                        templateUrl: 'scripts/app/entities/vehicle/requisition/vclRequisition-reject.html',
                        controller: 'VclRequisitionRejectController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vclRequisition');
                        $translatePartialLoader.addPart('requisitionTypes');
                        $translatePartialLoader.addPart('vehicleTypes');
                        $translatePartialLoader.addPart('requisitionStatus');
                        return $translate.refresh();
                    }],
                    entity: ['VclRequisition','$stateParams', function(VclRequisition,$stateParams) {
                        return VclRequisition.get({id : $stateParams.id});
                    }]
                }
            })
            .state('vclRequisition.approve', {
                parent: 'vclRequisition',
                url: '/{id}/approve',
                data: {
                    authorities: ['ROLE_USER'],
                },

                views: {
                    'vehicleManagementView@vehicle': {
                        templateUrl: 'scripts/app/entities/vehicle/requisition/vclRequisition-approve.html',
                        controller: 'VclRequisitionApproveController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vclRequisition');
                        $translatePartialLoader.addPart('requisitionTypes');
                        $translatePartialLoader.addPart('vehicleTypes');
                        $translatePartialLoader.addPart('requisitionStatus');
                        return $translate.refresh();
                    }],
                    entity: ['VclRequisition','$stateParams', function(VclRequisition,$stateParams) {
                        return VclRequisition.get({id : $stateParams.id});
                    }]
                }
            })
            .state('vclRequisition.reqdetail', {
                parent: 'vehicle',
                url: '/reqdetail/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.vclRequisition.detail.title'
                },
                views: {
                    'vehicleManagementView@vehicle': {
                        templateUrl: 'scripts/app/entities/vehicle/requisition/vclRequisition-approvedetail.html',
                        controller: 'VclRequisitionApproveDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vclRequisition');
                        $translatePartialLoader.addPart('requisitionTypes');
                        $translatePartialLoader.addPart('vehicleTypes');
                        $translatePartialLoader.addPart('requisitionStatus');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'VclRequisition', function($stateParams, VclRequisition) {
                        return VclRequisition.get({id : $stateParams.id});
                    }]
                }
            })
    });
