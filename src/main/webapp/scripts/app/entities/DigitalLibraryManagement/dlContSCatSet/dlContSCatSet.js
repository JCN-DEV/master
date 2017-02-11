'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('libraryInfo.dlContSCatSet', {
                parent: 'libraryInfo',
                url: '/dlContSCatSets',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlContSCatSet.home.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlContSCatSet/dlContSCatSets.html',
                        controller: 'DlContSCatSetController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlContSCatSet');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('libraryInfo.dlContSCatSet.detail', {
                parent: 'libraryInfo.dlContSCatSet',
                url: '/dlContSCatSet/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlContSCatSet.detail.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlContSCatSet/dlContSCatSet-detail.html',
                        controller: 'DlContSCatSetDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlContSCatSet');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DlContSCatSet', function($stateParams, DlContSCatSet) {
                        return DlContSCatSet.get({id : $stateParams.id});
                    }]
                }
            })
            .state('libraryInfo.dlContSCatSet.new', {
                parent: 'libraryInfo.dlContSCatSet',
                url: '/new',
                data: {
                    authorities: []
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlContSCatSet/dlContSCatSet-dialog.html',
                        controller: 'DlContSCatSetDialogController'
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
            .state('libraryInfo.dlContSCatSet.edit', {
                parent: 'libraryInfo.dlContSCatSet',
                url: '/{id}/edit',
                data: {
                    authorities: []
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlContSCatSet/dlContSCatSet-dialog.html',
                        controller: 'DlContSCatSetDialogController'
                    }
                },
                 resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlContSCatSet');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DlContSCatSet', function($stateParams, DlContSCatSet) {
/*
                        return DlContSCatSet.get({id : $stateParams.id});
*/
                    }]
                }
              /*  onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlContSCatSet/dlContSCatSet-dialog.html',
                        controller: 'DlContSCatSetDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DlContSCatSet', function(DlContSCatSet) {
                                return DlContSCatSet.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlContSCatSet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]*/
            })
            /*.state('libraryInfo.dlContSCatSet.edit', {
                parent: 'libraryInfo.dlContSCatSet',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlContSCatSet.detail.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlContSCatSet/dlContSCatSet-dialog.html',
                        controller: 'DlContSCatSetDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlContSCatSet');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DlContSCatSet', function($stateParams, DlContSCatSet) {
                        return DlContSCatSet.get({id : $stateParams.id});
                    }]
                }
            })
*/


            .state('libraryInfo.dlContSCatSet.delete', {
                parent: 'libraryInfo.dlContSCatSet',
                url: '/{id}/delete',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlContSCatSet/dlContSCatSet-delete-dialog.html',
                        controller: 'DlContSCatSetDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DlContSCatSet', function(DlContSCatSet) {
                                return DlContSCatSet.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlContSCatSet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
