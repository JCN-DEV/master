'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('libraryInfo.dlContTypeSet', {
                parent: 'libraryInfo',
                url: '/dlContTypeSets',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlContTypeSet.home.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlContTypeSet/dlContTypeSets.html',
                        controller: 'DlContTypeSetController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlContTypeSet');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('libraryInfo.dlContTypeSet.detail', {
                parent: 'libraryInfo.dlContTypeSet',
                url: '/dlContTypeSet/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlContTypeSet.detail.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlContTypeSet/dlContTypeSet-detail.html',
                        controller: 'DlContTypeSetDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlContTypeSet');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DlContTypeSet', function($stateParams, DlContTypeSet) {
                        return DlContTypeSet.get({id : $stateParams.id});
                    }]
                }
            })
            .state('libraryInfo.dlContTypeSet.new', {
                parent: 'libraryInfo.dlContTypeSet',
                url: '/new',
                data: {
                    authorities: []
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlContTypeSet/dlContTypeSet-dialog.html',
                        controller: 'DlContTypeSetDialogController'
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            code: null,
                            name: null,
                            description: null,
                            pStatus: null,
                            createdDate: null,
                            updatedDate: null,
                            createdBy: null,
                            updatedBy: null,
                            status: null,
                            id: null
                        };
                    }
                }

            })
            /*.state('libraryInfo.dlContTypeSet.edit', {
                parent: 'libraryInfo.dlContTypeSet',
                url: '/{id}/edit',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlContTypeSet/dlContTypeSet-dialog.html',
                        controller: 'DlContTypeSetDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DlContTypeSet', function(DlContTypeSet) {
                                return DlContTypeSet.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlContTypeSet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })*/
            .state('libraryInfo.dlContTypeSet.edit', {
                parent: 'libraryInfo.dlContTypeSet',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlContTypeSet.detail.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlContTypeSet/dlContTypeSet-dialog.html',
                        controller: 'DlContTypeSetDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlContTypeSet');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DlContTypeSet', function($stateParams, DlContTypeSet) {
                        return DlContTypeSet.get({id : $stateParams.id});
                    }]
                }
            })





            .state('libraryInfo.dlContTypeSet.delete', {
                parent: 'libraryInfo.dlContTypeSet',
                url: '/{id}/delete',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlContTypeSet/dlContTypeSet-delete-dialog.html',
                        controller: 'DlContTypeSetDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DlContTypeSet', function(DlContTypeSet) {
                                return DlContTypeSet.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlContTypeSet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
