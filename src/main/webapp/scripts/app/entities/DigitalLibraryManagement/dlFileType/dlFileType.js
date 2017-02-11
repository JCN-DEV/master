'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('libraryInfo.dlFileType', {
                parent: 'libraryInfo',
                url: '/dlFileTypes',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlFileType.home.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlFileType/dlFileTypes.html',
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
            .state('libraryInfo.dlFileType.detail', {
                parent: 'libraryInfo.dlFileType',
                url: '/dlFileType/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlFileType.detail.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlFileType/dlFileType-detail.html',
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
            .state('libraryInfo.dlFileType.new', {
                parent: 'libraryInfo.dlFileType',
                url: '/new',
                data: {
                    authorities: []
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlFileType/dlFileType-dialog.html',
                        controller: 'DlFileTypeDialogController'
                    }
                },
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
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlFileType/dlFileType-dialog.html',
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
                }]*/
            })
            .state('libraryInfo.dlFileType.edit', {
                parent: 'libraryInfo.dlFileType',
                url: '/{id}/edit',
                data: {
                    authorities: []
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlFileType/dlFileType-dialog.html',
                        controller: 'DlFileTypeDialogController'

                    }
                },
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                 $modal.open({
                 templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlFileType/dlFileType-dialog.html',
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
                 }]*/

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
            .state('libraryInfo.dlFileType.delete', {
                parent: 'libraryInfo.dlFileType',
                url: '/{id}/delete',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlFileType/dlFileType-delete-dialog.html',
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
