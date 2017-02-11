'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('courseInfo.cmsSubject', {
                parent: 'courseInfo',
                url: '/subjects',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.cmsSubject.home.title'
                },
                views: {
                    'courseView@courseInfo': {
                        templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsSubject/cmsSubjects.html',
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
            .state('courseInfo.cmsSubject.detail', {
                parent: 'courseInfo.cmsSubject',
                url: '/cmsSubject/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.cmsSubject.detail.title'
                },
                views: {
                    'courseView@courseInfo': {
                        templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsSubject/cmsSubject-detail.html',
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
            .state('courseInfo.cmsSubject.new', {
                parent: 'courseInfo.cmsSubject',
                url: '/new',
                data: {
                    authorities: [],
                },
                views: {
                   'courseView@courseInfo': {
                       templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsSubject/cmsSubject-dialog.html',
                       controller: 'CmsSubjectDialogController'
                   }
                },
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
               /* onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
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
                }]*/
            })
            .state('courseInfo.cmsSubject.edit', {
                parent: 'courseInfo.cmsSubject',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                },
                views: {
                    'courseView@courseInfo':{
                       templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsSubject/cmsSubject-dialog.html',
                       controller: 'CmsSubjectDialogController',
                    }
                 },
                 resolve: {
                         entity: ['$stateParams','CmsSubject', function($stateParams,CmsSubject) {
                          return CmsSubject.get({id : $stateParams.id});
                         }]
                     }
               /* onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
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
                }]*/
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
