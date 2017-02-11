'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('assetSupplier', {
                parent: 'assetManagements',
                url: '/assetSuppliers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetSupplier.home.title'
                },
                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetSupplier/assetSuppliers.html',
                        controller: 'AssetSupplierController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetSupplier');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('assetSupplier.detail', {
                parent: 'assetSupplier',
                url: '/assetSupplier/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetSupplier.detail.title'
                },
                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetSupplier/assetSupplier-detail.html',
                        controller: 'AssetSupplierDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetSupplier');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AssetSupplier', function($stateParams, AssetSupplier) {
                        return AssetSupplier.get({id : $stateParams.id});
                    }]
                }
            })
            .state('assetSupplier.new', {
                parent: 'assetSupplier',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetSupplier.home.title'
                },
                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetSupplier/assetSupplier-dialog.html',
                        controller: 'AssetSupplierDialogController'
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            name: null,
                            supplierId: null,
                            address: null,
                            products: null,
                            contactNo: null,
                            telephoneNo: null,
                            email: null,
                            webSite: null,
                            faxNo: null,
                            status:true,
                            id: null
                        };
                    }
                }

                //onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                //    $modal.open({
                //        templateUrl: 'scripts/app/entities/assetManagement/assetSupplier/assetSupplier-dialog.html',
                //        controller: 'AssetSupplierDialogController',
                //        size: 'lg',
                //        resolve: {
                //            entity: function () {
                //                return {
                //                    name: null,
                //                    supplierId: null,
                //                    address: null,
                //                    products: null,
                //                    contactNo: null,
                //                    telephoneNo: null,
                //                    email: null,
                //                    webSite: null,
                //                    faxNo: null,
                //                    id: null
                //                };
                //            }
                //        }
                //    }).result.then(function(result) {
                //        $state.go('assetSupplier', null, { reload: true });
                //    }, function() {
                //        $state.go('assetSupplier');
                //    })
                //}]
            })
            .state('assetSupplier.edit', {
                parent: 'assetSupplier',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'assetManagementView@assetManagements': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetSupplier/assetSupplier-dialog.html',
                        controller: 'AssetSupplierDialogController'

                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetSupplier');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AssetSupplier', function($stateParams, AssetSupplier) {
                       /* var assetSupplierStack = AssetSupplier.get({id : $stateParams.id});
                        var assetSupplier = null;
                        assetSupplier.id = assetSupplierStack.id;
                        assetSupplier.name = assetSupplierStack.name;
                        assetSupplier.contactNo = parseInt(assetSupplierStack.contactNo);
                        assetSupplier.products = assetSupplierStack.products;
                        assetSupplier.address = assetSupplierStack.address;
                        assetSupplier.telephoneNo = parseInt(assetSupplierStack.telephoneNo);
                        assetSupplier.email = assetSupplierStack.email;
                        assetSupplier.webSite = assetSupplierStack.webSite;
                        assetSupplier.faxNo = assetSupplierStack.faxNo;*/
                        return AssetSupplier.get({id : $stateParams.id});
                    }]
                }
                //onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                //    $modal.open({
                //        templateUrl: 'scripts/app/entities/assetManagement/assetSupplier/assetSupplier-dialog.html',
                //        controller: 'AssetSupplierDialogController',
                //        size: 'lg',
                //        resolve: {
                //            entity: ['AssetSupplier', function(AssetSupplier) {
                //                return AssetSupplier.get({id : $stateParams.id});
                //            }]
                //        }
                //    }).result.then(function(result) {
                //        $state.go('assetSupplier', null, { reload: true });
                //    }, function() {
                //        $state.go('^');
                //    })
                //}]
            });
    });
