'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('assetRecord', {
                parent: 'assetManagements',
                url: '/assetRecords',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetRecord.home.title'
                },
                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetRecord/assetRecords.html',
                        controller: 'AssetRecordController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetRecord');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('assetRecord.detail', {
                parent: 'assetRecord',
                url: '/assetRecord/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetRecord.detail.title'
                },
                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetRecord/assetRecord-detail.html',
                        controller: 'AssetRecordDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetRecord');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AssetRecord', function($stateParams, AssetRecord) {
                        return AssetRecord.get({id : $stateParams.id});
                    }]
                }
            })
            .state('assetRecord.new', {
                parent: 'assetRecord',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetRecord/assetRecord-dialog.html',
                        controller: 'AssetRecordDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetRecord');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            recordCode: 'AutoGenId',
                            assetName: null,
                            vendorName: null,
                            supplierName: null,
                            purchaseDate: null,
                            orderNo: null,
                            price: null,
                            status: true,
                            statusAsset: '1',
                            assetGroup: '1',
                            id: null
                        };
                    }
                }

            })
            //.state('assetRecord.new', {
            //    parent: 'assetRecord',
            //    url: '/new',
            //    data: {
            //        authorities: ['ROLE_USER'],
            //    },
            //
            //    views: {
            //        'assetManagementView@assetManagements': {
            //            templateUrl: 'scripts/app/entities/assetManagement/assetRecord/assetRecord-dialog.html',
            //            controller: 'AssetRecordDialogController'
            //        }
            //    },
            //    resolve: {
            //        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
            //            $translatePartialLoader.addPart('assetRecord');
            //            return $translate.refresh();
            //        }],
            //        entity: ['$stateParams', 'AssetRecord', function($stateParams, AssetRecord) {
            //            return null;
            //        }]
            //    }

                //onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                //    $modal.open({
                //        templateUrl: 'scripts/app/entities/assetManagement/assetRecord/assetRecord-dialog.html',
                //        controller: 'AssetRecordDialogController',
                //        size: 'lg',
                //        resolve: {
                //            entity: function () {
                //                return {
                //                    assetName: null,
                //                    vendorName: null,
                //                    supplierName: null,
                //                    purchaseDate: null,
                //                    orderNo: null,
                //                    price: null,
                //                    status: null,
                //                    id: null
                //                };
                //            }
                //        }
                //    }).result.then(function(result) {
                //        $state.go('assetRecord', null, { reload: true });
                //    }, function() {
                //        $state.go('assetRecord');
                //    })
                //}]
           // })
            .state('assetRecord.edit', {
                parent: 'assetRecord',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },

                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetRecord/assetRecord-dialog.html',
                        controller: 'AssetRecordDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetRecord');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AssetRecord', function($stateParams, AssetRecord) {
                        return AssetRecord.get({id : $stateParams.id});
                    }]
                }
                //onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                //    $modal.open({
                //        templateUrl: 'scripts/app/entities/assetManagement/assetRecord/assetRecord-dialog.html',
                //        controller: 'AssetRecordDialogController',
                //        size: 'lg',
                //        resolve: {
                //            entity: ['AssetRecord', function(AssetRecord) {
                //                return AssetRecord.get({id : $stateParams.id});
                //            }]
                //        }
                //    }).result.then(function(result) {
                //        $state.go('assetRecord', null, { reload: true });
                //    }, function() {
                //        $state.go('^');
                //    })
                //}]
            });
    });
