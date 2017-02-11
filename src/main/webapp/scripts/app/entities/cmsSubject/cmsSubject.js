'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cmsSubject', {
                parent: 'entity',
                url: '/cmsSubjects',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.cmsSubject.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cmsSubject/cmsSubjects.html',
                        controller: 'CmsSubjectController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cmsSubject');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('cmsSubject.detail', {
                parent: 'entity',
                url: '/cmsSubject/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.cmsSubject.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cmsSubject/cmsSubject-detail.html',
                        controller: 'CmsSubjectDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cmsSubject');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CmsSubject', function($stateParams, CmsSubject) {
                        return CmsSubject.get({id : $stateParams.id});
                    }]
                }
            })
            .state('cmsSubject.new', {
                parent: 'cmsSubject',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cmsSubject/cmsSubject-dialog.html',
                        controller: 'CmsSubjectDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    theoryCredHr: null,
                                    pracCredHr: null,
                                    totalCredHr: null,
                                    theoryCon: null,
                                    theoryFinal: null,
                                    pracCon: null,
                                    pracFinal: null,
                                    totalMarks: null,
                                    description: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('cmsSubject', null, { reload: true });
                    }, function() {
                        $state.go('cmsSubject');
                    })
                }]
            })
            .state('cmsSubject.edit', {
                parent: 'cmsSubject',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cmsSubject/cmsSubject-dialog.html',
                        controller: 'CmsSubjectDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CmsSubject', function(CmsSubject) {
                                return CmsSubject.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cmsSubject', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('cmsSubject.delete', {
                parent: 'cmsSubject',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cmsSubject/cmsSubject-delete-dialog.html',
                        controller: 'CmsSubjectDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CmsSubject', function(CmsSubject) {
                                return CmsSubject.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cmsSubject', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
