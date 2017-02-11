'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('dlBookIssue', {
                parent: 'entity',
                url: '/dlBookIssues',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.dlBookIssue.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dlBookIssue/dlBookIssues.html',
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
            .state('dlBookIssue.detail', {
                parent: 'entity',
                url: '/dlBookIssue/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.dlBookIssue.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dlBookIssue/dlBookIssue-detail.html',
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
            .state('dlBookIssue.new', {
                parent: 'dlBookIssue',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlBookIssue/dlBookIssue-dialog.html',
                        controller: 'DlBookIssueDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    isbnNo: null,
                                    noOfCopies: null,
                                    returnDate: null,
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
                        $state.go('dlBookIssue', null, { reload: true });
                    }, function() {
                        $state.go('dlBookIssue');
                    })
                }]
            })
            .state('dlBookIssue.edit', {
                parent: 'dlBookIssue',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlBookIssue/dlBookIssue-dialog.html',
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
                }]
            })
            .state('dlBookIssue.delete', {
                parent: 'dlBookIssue',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlBookIssue/dlBookIssue-delete-dialog.html',
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
