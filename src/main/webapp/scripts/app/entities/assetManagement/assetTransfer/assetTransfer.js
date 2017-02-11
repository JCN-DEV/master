'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('assetTransfer', {
                parent: 'assetManagements',
                url: '/assetTransfers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetTransfer.home.title'
                },
                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetTransfer/assetTransfers.html',
                        controller: 'AssetTransferController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetTransfer');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('assetTransfer.detail', {
                parent: 'assetManagements',
                url: '/assetTransfer/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetTransfer.detail.title'
                },
                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetTransfer/assetTransfer-detail.html',
                        controller: 'AssetTransferDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetTransfer');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AssetTransfer', function($stateParams, AssetTransfer) {
                        return AssetTransfer.get({id : $stateParams.id});
                    }]
                }
            })
            .state('assetTransfer.new', {
                parent: 'assetTransfer',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetTransfer.detail.title'
                },

                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetTransfer/assetTransfer-dialog.html',
                        controller: 'AssetTransferDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetTransfer');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AssetTransfer', function($stateParams, AssetTransfer) {
                        return null;
                    }]
                }

                //onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                //    $modal.open({
                //        templateUrl: 'scripts/app/entities/assetManagement/assetTransfer/assetTransfer-dialog.html',
                //        controller: 'AssetTransferDialogController',
                //        size: 'lg',
                //        resolve: {
                //            entity: function () {
                //                return {
                //                    date: null,
                //                    id: null
                //                };
                //            }
                //        }
                //    }).result.then(function(result) {
                //        $state.go('assetTransfer', null, { reload: true });
                //    }, function() {
                //        $state.go('assetTransfer');
                //    })
                //}]
            })
            .state('assetTransfer.edit', {
                parent: 'assetTransfer',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetTransfer.detail.title'
                },
                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetTransfer/assetTransfer-dialog.html',
                        controller: 'AssetTransferDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetTransfer');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AssetTransfer', function($stateParams, AssetTransfer) {
                        return AssetTransfer.get({id : $stateParams.id});
                    }]
                }



                //onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                //    $modal.open({
                //        templateUrl: 'scripts/app/entities/assetManagement/assetTransfer/assetTransfer-dialog.html',
                //        controller: 'AssetTransferDialogController',
                //        size: 'lg',
                //        resolve: {
                //            entity: ['AssetTransfer', function(AssetTransfer) {
                //                return AssetTransfer.get({id : $stateParams.id});
                //            }]
                //        }
                //    }).result.then(function(result) {
                //        $state.go('assetTransfer', null, { reload: true });
                //    }, function() {
                //        $state.go('^');
                //    })
                //}]
            });
    });
