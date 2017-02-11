'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('dlFileType', {
                parent: 'entity',
                url: '/dlFileTypes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.dlFileType.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dlFileType/dlFileTypes.html',
                        controller: 'DlFileTypeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlFileType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('dlFileType.detail', {
                parent: 'entity',
                url: '/dlFileType/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.dlFileType.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dlFileType/dlFileType-detail.html',
                        controller: 'DlFileTypeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlFileType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DlFileType', function($stateParams, DlFileType) {
                        return DlFileType.get({id : $stateParams.id});
                    }]
                }
            })
            .state('dlFileType.new', {
                parent: 'dlFileType',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlFileType/dlFileType-dialog.html',
                        controller: 'DlFileTypeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    fileType: null,
                                    limitMb: null,
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
                        $state.go('dlFileType', null, { reload: true });
                    }, function() {
                        $state.go('dlFileType');
                    })
                }]
            })
            .state('dlFileType.edit', {
                parent: 'dlFileType',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlFileType/dlFileType-dialog.html',
                        controller: 'DlFileTypeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DlFileType', function(DlFileType) {
                                return DlFileType.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlFileType', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('dlFileType.delete', {
                parent: 'dlFileType',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlFileType/dlFileType-delete-dialog.html',
                        controller: 'DlFileTypeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DlFileType', function(DlFileType) {
                                return DlFileType.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlFileType', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
