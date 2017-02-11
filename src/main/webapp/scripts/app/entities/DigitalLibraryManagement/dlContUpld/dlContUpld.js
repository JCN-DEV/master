'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('libraryInfo.dlContUpld', {
                parent: 'libraryInfo',
                url: '/dlContUplds',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlContUpld.home.title'
                },
                views: {
                   'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlContUpld/dlContUplds.html',
                        controller: 'DlContUpldController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlContUpld');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })

             .state('libraryInfo.dlContUpldByUser', {
                parent: 'libraryInfo',
                url: '/dlContUpldsByUser',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlContUpld.home.title'
                },
                views: {
                   'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlContUpld/dlContUpldsByUser.html',
                        controller: 'DlContUpldController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlContUpld');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('libraryInfo.dlContUpld.detail', {
                parent: 'libraryInfo.dlContUpld',
                url: '/dlContUpld/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlContUpld.detail.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlContUpld/dlContUpld-detail.html',
                        controller: 'DlContUpldDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlContUpld');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DlContUpld', function($stateParams, DlContUpld) {
                        return DlContUpld.get({id : $stateParams.id});
                    }]
                }
            })
            .state('libraryInfo.dlContUpld.new', {
                parent: 'libraryInfo.dlContUpld',
                url: '/new',
                data: {
                    authorities: []
                },
                views: {
                   'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlContUpld/dlContUpld-dialog.html',
                        controller: 'DlContUpldDialogController'
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            code: null,
                            authName: null,
                            edition: null,
                            isbnNo: null,
                            copyright: null,
                            publisher: null,
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

            .state('libraryInfo.dlContUpld.edit', {
                parent: 'libraryInfo.dlContUpld',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlContUpld.detail.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlContUpld/dlContUpld-dialog.html',
                        controller: 'DlContUpldDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlContUpld');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DlContUpld', function($stateParams, DlContUpld) {
                        return DlContUpld.get({id : $stateParams.id});
                    }]
                }
            })
            .state('libraryInfo.dlContUpld.delete', {
                parent: 'libraryInfo.dlContUpld',
                url: '/{id}/delete',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlContUpld/dlContUpld-delete-dialog.html',
                        controller: 'DlContUpldDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DlContUpld', function(DlContUpld) {
                                return DlContUpld.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlContUpld', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('libraryInfo.dlContUpldByUser.Approve', {
                parent: 'libraryInfo.dlContUpldByUser',
                url: '/approve/{id}',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlContUpld/dlContUpld-approve-dialog.html',
                        controller: 'DlContUpldApproveController',
                        size: 'md',
                        resolve: {
                            entity: ['DlContUpld', function(DlContUpld) {
                                return DlContUpld.get({id : $stateParams.id});
                            }]
                        }

                    }).result.then(function(result) {
                        $state.go('dlContUpld', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('libraryInfo.dlContUpld.new.alert', {
                parent: 'libraryInfo.dlContUpld.new',
                url: '/alert',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlContUpld/dlContUpld-delete-dialog.html',
                        controller: 'DlContUpldDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DlContUpld', function(DlContUpld) {
                                return DlContUpld.get({id : $stateParams.id});
                            }]
                        }
                    })
                }]
            });
    });
