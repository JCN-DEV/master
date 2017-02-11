'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('libraryInfo.dlContCatSet', {
                parent: 'libraryInfo',
                url: '/dlContCatSets',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlContCatSet.home.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlContCatSet/dlContCatSets.html',
                        controller: 'DlContCatSetController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlContCatSet');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('libraryInfo.dlContCatSet.detail', {
                parent: 'libraryInfo.dlContCatSet',
                url: '/dlContCatSet/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlContCatSet.detail.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlContCatSet/dlContCatSet-detail.html',
                        controller: 'DlContCatSetDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlContCatSet');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DlContCatSet', function($stateParams, DlContCatSet) {
                        return DlContCatSet.get({id : $stateParams.id});
                    }]
                }
            })
            .state('libraryInfo.dlContCatSet.new', {
                parent: 'libraryInfo.dlContCatSet',
                url: '/new',
                data: {
                    authorities: [],
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlContCatSet/dlContCatSet-dialog.html',
                        controller: 'DlContCatSetDialogController'
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
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlContCatSet/dlContCatSet-dialog.html',
                        controller: 'DlContCatSetDialogController',
                        size: 'lg',
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
                    }).result.then(function(result) {
                        $state.go('dlContCatSet', null, { reload: true });
                    }, function() {
                        $state.go('dlContCatSet');
                    })
                }]*/
            })
            /*.state('libraryInfo.dlContCatSet.edit', {
                parent: 'libraryInfo.dlContCatSet',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlContCatSet/dlContCatSet-dialog.html',
                        controller: 'DlContCatSetDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DlContCatSet', function(DlContCatSet) {
                                return DlContCatSet.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlContCatSet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })*/

            .state('libraryInfo.dlContCatSet.edit', {
                parent: 'libraryInfo.dlContCatSet',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlContCatSet.detail.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlContCatSet/dlContCatSet-dialog.html',
                        controller: 'DlContCatSetDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlContCatSet');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DlContCatSet', function($stateParams, DlContCatSet) {
                        //return DlContCatSet.get({id : $stateParams.id});
                    }]
                }
            })


            .state('libraryInfo.dlContCatSet.delete', {
                parent: 'libraryInfo.dlContCatSet',
                url: '/{id}/delete',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement//dlContCatSet/dlContCatSet-delete-dialog.html',
                        controller: 'DlContCatSetDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DlContCatSet', function(DlContCatSet) {
                                return DlContCatSet.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlContCatSet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
