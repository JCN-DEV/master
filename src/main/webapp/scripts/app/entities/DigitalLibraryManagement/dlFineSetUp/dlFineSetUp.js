'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('libraryInfo.dlFineSetUp', {
                parent: 'libraryInfo',
                url: '/dlFineSetUps',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlFineSetUp.home.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlFineSetUp/dlFineSetUps.html',
                        controller: 'DlFineSetUpController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlFineSetUp');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('libraryInfo.dlFineSetUp.detail', {
                parent: 'libraryInfo.dlFineSetUp',
                url: '/dlFineSetUp/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlFineSetUp.detail.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlFineSetUp/dlFineSetUp-detail.html',
                        controller: 'DlFineSetUpDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlFineSetUp');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DlFineSetUp', function($stateParams, DlFineSetUp) {
                        return DlFineSetUp.get({id : $stateParams.id});
                    }]
                }
            })
            .state('libraryInfo.dlFineSetUp.new', {
                parent: 'libraryInfo.dlFineSetUp',
                url: '/new',
                data: {
                    authorities: [],
                },
                 views: {
                        'libraryView@libraryInfo': {
                            templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlFineSetUp/dlFineSetUp-dialog.html',
                            controller: 'DlFineSetUpDialogController'
                        }
                    },
                    resolve: {
                               entity: function () {
                                   return {
                                       timeLimit: null,
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
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlFineSetUp/dlFineSetUp-dialog.html',
                        controller: 'DlFineSetUpDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    timeLimit: null,
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
                        $state.go('dlFineSetUp', null, { reload: true });
                    }, function() {
                        $state.go('dlFineSetUp');
                    })
                }]*/
            })
            .state('libraryInfo.dlFineSetUp.edit', {
                parent: 'libraryInfo.dlFineSetUp',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                },
                views: {
                        'libraryView@libraryInfo': {
                             templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlFineSetUp/dlFineSetUp-dialog.html',
                             controller: 'DlFineSetUpDialogController'
                        }
                    },
                    resolve: {
                            entity: ['DlFineSetUp','$stateParams', function(DlFineSetUp,$stateParams) {
                                return DlFineSetUp.get({id : $stateParams.id});
                            }]
                        }
               /* onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlFineSetUp/dlFineSetUp-dialog.html',
                        controller: 'DlFineSetUpDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DlFineSetUp', function(DlFineSetUp) {
                                return DlFineSetUp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlFineSetUp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]*/
            })
           /* .state('dlFineSetUp.delete', {
                parent: 'dlFineSetUp',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlFineSetUp/dlFineSetUp-delete-dialog.html',
                        controller: 'DlFineSetUpDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DlFineSetUp', function(DlFineSetUp) {
                                return DlFineSetUp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlFineSetUp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });*/
    });
