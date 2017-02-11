'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('vclRequisition', {
                parent: 'entity',
                url: '/vclRequisitions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.vclRequisition.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/vclRequisition/vclRequisitions.html',
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
                parent: 'entity',
                url: '/vclRequisition/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.vclRequisition.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/vclRequisition/vclRequisition-detail.html',
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
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/vclRequisition/vclRequisition-dialog.html',
                        controller: 'VclRequisitionDialogController',
                        size: 'lg',
                        resolve: {
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
                    }).result.then(function(result) {
                        $state.go('vclRequisition', null, { reload: true });
                    }, function() {
                        $state.go('vclRequisition');
                    })
                }]
            })
            .state('vclRequisition.edit', {
                parent: 'vclRequisition',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/vclRequisition/vclRequisition-dialog.html',
                        controller: 'VclRequisitionDialogController',
                        size: 'lg',
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
            .state('vclRequisition.delete', {
                parent: 'vclRequisition',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/vclRequisition/vclRequisition-delete-dialog.html',
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
            });
    });
