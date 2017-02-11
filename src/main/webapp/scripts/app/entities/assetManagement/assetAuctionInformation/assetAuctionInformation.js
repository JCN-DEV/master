'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('assetAuctionInformation', {
                parent: 'assetManagements',
                url: '/assetAuctionInformations',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetAuctionInformation.home.title'
                },
                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetAuctionInformation/assetAuctionInformations.html',
                        controller: 'AssetAuctionInformationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetAuctionInformation');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('assetAuctionInformation.detail', {
                parent: 'assetManagements',
                url: '/assetAuctionInformation/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetAuctionInformation.detail.title'
                },
                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetAuctionInformation/assetAuctionInformation-detail.html',
                        controller: 'AssetAuctionInformationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetAuctionInformation');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AssetAuctionInformation', function($stateParams, AssetAuctionInformation) {
                        return AssetAuctionInformation.get({id : $stateParams.id});
                    }]
                }
            })
            .state('assetAuctionInformation.new', {
                parent: 'assetAuctionInformation',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },

                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetAuctionInformation/assetAuctionInformation-dialog.html',
                        controller: 'AssetAuctionInformationDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetAuctionInformation');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AssetAuctionInformation', function($stateParams, AssetAuctionInformation) {
                        return null;
                    }]
                }
                //onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                //    $modal.open({
                //        templateUrl: 'scripts/app/entities/assetManagement/assetAuctionInformation/assetAuctionInformation-dialog.html',
                //        controller: 'AssetAuctionInformationDialogController',
                //        size: 'lg',
                //        resolve: {
                //            entity: function () {
                //                return {
                //                    lastRepairDate: null,
                //                    isAuctionBefore: null,
                //                    id: null
                //                };
                //            }
                //        }
                //    }).result.then(function(result) {
                //        $state.go('assetAuctionInformation', null, { reload: true });
                //    }, function() {
                //        $state.go('assetAuctionInformation');
                //    })
                //}]
            })
            .state('assetAuctionInformation.edit', {
                parent: 'assetAuctionInformation',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },

                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetAuctionInformation/assetAuctionInformation-dialog.html',
                        controller: 'AssetAuctionInformationDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetAuctionInformation');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AssetAuctionInformation', function($stateParams, AssetAuctionInformation) {
                        return AssetAuctionInformation.get({id : $stateParams.id});
                    }]
                }

                //onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                //    $modal.open({
                //        templateUrl: 'scripts/app/entities/assetManagement/assetAuctionInformation/assetAuctionInformation-dialog.html',
                //        controller: 'AssetAuctionInformationDialogController',
                //        size: 'lg',
                //        resolve: {
                //            entity: ['AssetAuctionInformation', function(AssetAuctionInformation) {
                //                return AssetAuctionInformation.get({id : $stateParams.id});
                //            }]
                //        }
                //    }).result.then(function(result) {
                //        $state.go('assetAuctionInformation', null, { reload: true });
                //    }, function() {
                //        $state.go('^');
                //    })
                //}]
            });
    });
