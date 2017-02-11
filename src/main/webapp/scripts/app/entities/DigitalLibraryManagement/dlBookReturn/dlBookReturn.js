'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('libraryInfo.dlBookReturn', {
                parent: 'libraryInfo',
                url: '/dlBookReturns',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlBookReturn.home.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlBookReturn/dlBookReturns.html',
                        controller: 'DlBookReturnController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlBookReturn');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })


              .state('libraryInfo.dlClearance', {
                            parent: 'libraryInfo',
                            url: '/dlClearance',
                           data: {
                               authorities: [],
                              pageTitle: 'stepApp.dlBookReturn.home.title'
                           },
                            views: {
                                'libraryView@libraryInfo': {
                                    templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlBookReturn/dlClearance.html',
                                   controller: 'DlBookReturnController'
                                }
                            },
                            resolve: {
                                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                                    $translatePartialLoader.addPart('dlBookReturn');
                                    $translatePartialLoader.addPart('global');
                                   return $translate.refresh();
                               }]
                            }
                        })
            .state('libraryInfo.dlBookReturn.detail', {
                parent: 'libraryInfo.dlBookReturn',
                url: '/dlBookReturn/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlBookReturn.detail.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlBookReturn/dlBookReturn-detail.html',
                        controller: 'DlBookReturnDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlBookReturn');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DlBookReturn', function($stateParams, DlBookReturn) {
                        return DlBookReturn.get({id : $stateParams.id});
                    }]
                }
            })
            .state('libraryInfo.dlBookReturn.new', {
                parent: 'libraryInfo.dlBookReturn',
                url: '/new',
                data: {
                    authorities: [],
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlBookReturn/dlBookReturn-dialog.html',
                        controller: 'DlBookReturnDialogController'
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            issueId: null,
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
            .state('libraryInfo.dlBookReturn.edit', {
                parent: 'libraryInfo.dlBookReturn',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlBookReturn.detail.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlBookReturn/dlBookReturn-dialog.html',
                        controller: 'DlBookReturnDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlBookReturn');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DlBookReturn', function($stateParams, DlBookReturn) {
                        return DlBookReturn.get({id : $stateParams.id});
                    }]
                }
            })
            .state('libraryInfo.dlBookReturn.delete', {
                parent: 'libraryInfo.dlBookReturn',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlBookReturn/dlBookReturn-delete-dialog.html',
                        controller: 'DlBookReturnDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DlBookReturn', function(DlBookReturn) {
                                return DlBookReturn.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlBookReturn', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });






//
//.state('libraryInfo.dlRequisitionByUser.Approve', {
//                parent: 'libraryInfo.dlRequisitionByUser',
//                url: '/approve/{id}',
//                data: {
//                    authorities: []
//                },
//                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
//                    $modal.open({
//                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlBookIssue/dlBookRequisition-approve-dialog.html',
//                        controller: 'dlBookRequisitionApproveController',
//                        size: 'md',
//                        resolve: {
//                            entity: ['DlBookIssue', function(DlBookIssue) {
//                                return DlBookIssue.get({id : $stateParams.id});
//                            }]
//                        }
//
//                    }).result.then(function(result) {
//                        $state.go('libraryInfo.dlBookIssue', null, { reload: true });
//                    }, function() {
//                        $state.go('^');
//                    })
//                }]
//            })



    });
