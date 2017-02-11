'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('libraryInfo.dlSourceSetUp', {
                parent: 'libraryInfo',
                url: '/dlSourceSetUps',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlSourceSetUp.home.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlSourceSetUp/dlSourceSetUps.html',
                        controller: 'DlSourceSetUpController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlSourceSetUp');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('libraryInfo.dlSourceSetUp.detail', {
                parent: 'libraryInfo.dlSourceSetUp',
                url: '/dlSourceSetUp/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlSourceSetUp.detail.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlSourceSetUp/dlSourceSetUp-detail.html',
                        controller: 'DlSourceSetUpDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlSourceSetUp');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DlSourceSetUp', function($stateParams, DlSourceSetUp) {
                        return DlSourceSetUp.get({id : $stateParams.id});
                    }]
                }
            })
            .state('libraryInfo.dlSourceSetUp.new', {
                parent: 'libraryInfo.dlSourceSetUp',
                url: '/new',
                data: {
                    authorities: [],
                },
            views: {
                    'libraryView@libraryInfo': {
                      templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlSourceSetUp/dlSourceSetUp-dialog.html',
                      controller: 'DlSourceSetUpDialogController'
                    }
                },
                resolve: {
                        entity: function () {
                            return {
                                name: null,
                                fine: null,
                                status: null,
                                createDate: null,
                                createBy: null,
                                updateDate: null,
                                updateBy: null,
                                id: null
                            };
                        }
                    }
               /* onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlSourceSetUp/dlSourceSetUp-dialog.html',
                        controller: 'DlSourceSetUpDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    fine: null,
                                    status: null,
                                    createDate: null,
                                    createBy: null,
                                    updateDate: null,
                                    updateBy: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('dlSourceSetUp', null, { reload: true });
                    }, function() {
                        $state.go('dlSourceSetUp');
                    })
                }]*/
            })
            .state('libraryInfo.dlSourceSetUp.edit', {
                parent: 'libraryInfo.dlSourceSetUp',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                },
                views: {
                    'libraryView@libraryInfo': {
                       templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlSourceSetUp/dlSourceSetUp-dialog.html',
                       controller: 'DlSourceSetUpDialogController'
                    }
                },
                 resolve: {
                        entity: ['$stateParams','DlSourceSetUp', function($stateParams,DlSourceSetUp) {
                            return DlSourceSetUp.get({id : $stateParams.id});
                        }]
                    }
               /* onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlSourceSetUp/dlSourceSetUp-dialog.html',
                        controller: 'DlSourceSetUpDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DlSourceSetUp', function(DlSourceSetUp) {
                                return DlSourceSetUp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlSourceSetUp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]*/
            })
            .state('libraryInfo.dlSourceSetUp.delete', {
                parent: 'libraryInfo.dlSourceSetUp',
                url: '/{id}/delete',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlSourceSetUp/dlSourceSetUp-delete-dialog.html',
                        controller: 'DlSourceSetUpDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DlSourceSetUp', function(DlSourceSetUp) {
                                return DlSourceSetUp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlSourceSetUp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
