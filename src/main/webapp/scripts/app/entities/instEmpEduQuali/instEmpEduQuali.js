'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instEmpEduQuali', {
                parent: 'entity',
                url: '/instEmpEduQualis',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmpEduQuali.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmpEduQuali/instEmpEduQualis.html',
                        controller: 'InstEmpEduQualiController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmpEduQuali');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instEmpEduQuali.detail', {
                parent: 'entity',
                url: '/instEmpEduQuali/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmpEduQuali.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmpEduQuali/instEmpEduQuali-detail.html',
                        controller: 'InstEmpEduQualiDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmpEduQuali');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstEmpEduQuali', function($stateParams, InstEmpEduQuali) {
                        return InstEmpEduQuali.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instEmpEduQuali.new', {
                parent: 'instEmpEduQuali',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmpEduQuali/instEmpEduQuali-dialog.html',
                        controller: 'InstEmpEduQualiDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    certificateName: null,
                                    board: null,
                                    session: null,
                                    semester: null,
                                    rollNo: null,
                                    passingYear: null,
                                    cgpa: null,
                                    certificateCopy: null,
                                    certificateCopyContentType: null,
                                    status: null,
                                    groupSubject: null,
                                    resultPublishDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instEmpEduQuali', null, { reload: true });
                    }, function() {
                        $state.go('instEmpEduQuali');
                    })
                }]
            })
            .state('instEmpEduQuali.edit', {
                parent: 'instEmpEduQuali',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmpEduQuali/instEmpEduQuali-dialog.html',
                        controller: 'InstEmpEduQualiDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstEmpEduQuali', function(InstEmpEduQuali) {
                                return InstEmpEduQuali.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmpEduQuali', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instEmpEduQuali.delete', {
                parent: 'instEmpEduQuali',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmpEduQuali/instEmpEduQuali-delete-dialog.html',
                        controller: 'InstEmpEduQualiDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstEmpEduQuali', function(InstEmpEduQuali) {
                                return InstEmpEduQuali.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmpEduQuali', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
