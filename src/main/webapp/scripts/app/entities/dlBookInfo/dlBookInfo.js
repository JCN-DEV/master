'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('dlBookInfo', {
                parent: 'entity',
                url: '/dlBookInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.dlBookInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dlBookInfo/dlBookInfos.html',
                        controller: 'DlBookInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlBookInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('dlBookInfo.detail', {
                parent: 'entity',
                url: '/dlBookInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.dlBookInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dlBookInfo/dlBookInfo-detail.html',
                        controller: 'DlBookInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlBookInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DlBookInfo', function($stateParams, DlBookInfo) {
                        return DlBookInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('dlBookInfo.new', {
                parent: 'dlBookInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlBookInfo/dlBookInfo-dialog.html',
                        controller: 'DlBookInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    title: null,
                                    edition: null,
                                    isbnNo: null,
                                    authorName: null,
                                    copyright: null,
                                    publisherName: null,
                                    libraryName: null,
                                    callNo: null,
                                    totalCopies: null,
                                    purchaseDate: null,
                                    createdDate: null,
                                    updatedDate: null,
                                    createdBy: null,
                                    updatedBy: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('dlBookInfo', null, { reload: true });
                    }, function() {
                        $state.go('dlBookInfo');
                    })
                }]
            })
            .state('dlBookInfo.edit', {
                parent: 'dlBookInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlBookInfo/dlBookInfo-dialog.html',
                        controller: 'DlBookInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DlBookInfo', function(DlBookInfo) {
                                return DlBookInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlBookInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('dlBookInfo.delete', {
                parent: 'dlBookInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlBookInfo/dlBookInfo-delete-dialog.html',
                        controller: 'DlBookInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DlBookInfo', function(DlBookInfo) {
                                return DlBookInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlBookInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
