'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('assetRequisition', {
                parent: 'assetManagements',
                url: '/assetRequisitions',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.assetRequisition.home.title'
                },
                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetRequisition/assetRequisitions.html',
                        controller: 'AssetRequisitionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetRequisition');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('assetRequisition.detail', {
                parent: 'assetRequisition',
                url: '/assetRequisition/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.assetRequisition.detail.title'
                },
                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetRequisition/assetRequisition-detail.html',
                        controller: 'AssetRequisitionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetRequisition');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AssetRequisition', function($stateParams, AssetRequisition) {
                        return AssetRequisition.get({id : $stateParams.id});
                    }]
                }
            })
            .state('assetRequisition.new', {
                parent: 'assetRequisition',
                url: '/new',
                data: {
                    authorities: [],
                },
                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetRequisition/assetRequisition-dialog.html',
                        controller: 'AssetRequisitionDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetRequisition');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AssetRequisition', function($stateParams, AssetRequisition) {
                        return null;
                    }]
                }
            })
            /*.state('assetRequisition.new', {
                parent: 'assetRequisition',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/assetRequisition/assetRequisition-dialog.html',
                        controller: 'AssetRequisitionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    empId: null,
                                    empName: null,
                                    designation: null,
                                    department: null,
                                    requisitionId: null,
                                    requisitionDate: null,
                                    quantity: null,
                                    reasonOfReq: null,
                                    reqStatus: null,
                                    status: null,
                                    createDate: null,
                                    createBy: null,
                                    updateDate: null,
                                    updateBy: null,
                                    remarks: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('assetRequisition', null, { reload: true });
                    }, function() {
                        $state.go('assetRequisition');
                    })
                }]
            })*/
            .state('assetRequisition.edit', {
                parent: 'assetRequisition',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                },

                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetRequisition/assetRequisition-dialog.html',
                        controller: 'AssetRequisitionDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetRequisition');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AssetRequisition', function($stateParams, AssetRequisition) {
                        return AssetRequisition.get({id : $stateParams.id});
                    }]
                }
                /*parent: 'assetRequisition',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/assetManagement/assetRequisition/assetRequisition-dialog.html',
                        controller: 'AssetRequisitionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['AssetRequisition', function(AssetRequisition) {
                                return AssetRequisition.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('assetRequisition', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]*/
            });
    });
