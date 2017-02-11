'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('libraryInfo.dlBookIssue', {
                parent: 'libraryInfo',
                url: '/dlBookIssues',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlBookIssue.home.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlBookIssue/dlBookIssues.html',
                        controller: 'DlBookIssueController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlBookIssue');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })

            .state('libraryInfo.dlRequisitionByUser', {
                parent: 'libraryInfo',
                url: '/dlRequisitionByUser',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlBookIssue.home.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlBookIssue/dlRequisitionByUser.html',
                        controller: 'DlBookIssueController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlBookIssue');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })

     /*       .state('libraryInfo.dlRequisitionByUserDeatail', {
                parent: 'libraryInfo',
                url: '/dlRequisitionDetail/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlBookIssue.home.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlBookIssue/dlBookIssue-detail.html',
                        controller: 'DlBookIssueDialogController'
                    }
                },
                resolve: {
                       translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                           $translatePartialLoader.addPart('dlBookIssue');
                           return $translate.refresh();
                       }],
                       entity: ['$stateParams', 'DlBookIssue', function($stateParams, DlBookIssue) {
                           return DlBookIssue.get({id : $stateParams.id});
                       }]
                   }
            })*/
            .state('libraryInfo.dlBookIssue.detail', {
                parent: 'libraryInfo.dlBookIssue',
                url: '/dlBookIssue/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlBookIssue.detail.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlBookIssue/dlBookIssue-detail.html',
                        controller: 'DlBookIssueDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlBookIssue');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DlBookIssue', function($stateParams, DlBookIssue) {
                        return DlBookIssue.get({id : $stateParams.id});
                    }]
                }
            })
            .state('libraryInfo.dlBookIssue.new', {
                parent: 'libraryInfo.dlBookIssue',
                url: '/new',
                data: {
                    authorities: [],
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlBookIssue/dlBookIssue-dialog.html',
                        controller: 'DlBookIssueDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlBookIssue');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            isbnNo: null,
                            noOfCopies: null,
                            returnDate: null,
                            createdDate: null,
                            updatedDate: null,
                            createdBy: null,
                            updatedBy: null,
                            id: null
                        };
                    }
                }

            })
            /*.state('libraryInfo.dlBookIssue.edit', {
                parent: 'libraryInfo.dlBookIssue',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlBookIssue/dlBookIssue-dialog.html',
                        controller: 'DlBookIssueDialogController'
                    }
                },
                resolve: {
                    entity: ['DlBookIssue', function(DlBookIssue) {
                        return DlBookIssue.get({id : $stateParams.id});
                    }]
                }
                *//*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlBookIssue/dlBookIssue-dialog.html',
                        controller: 'DlBookIssueDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DlBookIssue', function(DlBookIssue) {
                                return DlBookIssue.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlBookIssue', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]*//*
            })*/
            .state('libraryInfo.dlBookIssue.edit', {
                parent: 'libraryInfo.dlBookIssue',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlBookIssue.detail.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlBookIssue/dlBookIssue-dialog.html',
                        controller: 'DlBookIssueDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlBookIssue');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DlBookIssue', function($stateParams, DlBookIssue) {
                        return DlBookIssue.get({id : $stateParams.id});
                    }]
                }
            })

.state('libraryInfo.dlRequisitionByUser.Approve', {
                parent: 'libraryInfo.dlRequisitionByUser',
                url: '/approve/{id}',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlBookIssue/dlBookRequisition-approve-dialog.html',
                        controller: 'dlBookRequisitionApproveController',
                        size: 'md',
                        resolve: {
                            entity: ['DlBookIssue', function(DlBookIssue) {
                                return DlBookIssue.get({id : $stateParams.id});
                            }]
                        }

                    }).result.then(function(result) {
                        $state.go('libraryInfo.dlBookIssue', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })




            .state('libraryInfo.dlBookIssue.delete', {
                parent: 'libraryInfo.dlBookIssue',
                url: '/{id}/delete',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlBookIssue/dlBookIssue-delete-dialog.html',
                        controller: 'DlBookIssueDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DlBookIssue', function(DlBookIssue) {
                                return DlBookIssue.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlBookIssue', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
