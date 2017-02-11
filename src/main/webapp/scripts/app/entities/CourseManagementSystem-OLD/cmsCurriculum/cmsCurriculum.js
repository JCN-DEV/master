'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
           /*.state('cmsCurriculum', {
                parent: 'courseInfo',
                url: '/cmsCurriculums',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.cmsCurriculum.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cmsCurriculum/cmsCurriculums.html',
                        controller: 'CmsCurriculumController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cmsCurriculum');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })*/
            .state('courseInfo.curriculum.detail', {
                parent: 'courseInfo.curriculum',
                url: '/curriculum/{id}',

                data: {
                    authorities: [],
                    pageTitle: 'stepApp.cmsCurriculum.detail.title'
                },
               views: {
                        'courseView@courseInfo':{
                           templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsCurriculum/cmsCurriculum-detail.html',
                           controller: 'CmsCurriculumDetailController'
                        }
                     },
            resolve:
             {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cmsCurriculum');
                    return $translate.refresh();
                }],
               entity: ['$stateParams', 'CmsCurriculum', function($stateParams, CmsCurriculum) {
                    return CmsCurriculum.get({id : $stateParams.id});
                }]
            }
            })
             .state('courseInfo.curriculum', {
                parent: 'courseInfo',
                url: '/curriculum-list',
                /*/{id}*/
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.cmsCurriculum.detail.title'
                },
               views: {
                        'courseView@courseInfo':{
                           templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsCurriculum/cmsCurriculums.html',
                           controller: 'CmsCurriculumController'
                        }
                     },
            resolve:
             {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cmsCurriculum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CmsCurriculum', function($stateParams, CmsCurriculum) {
                    return CmsCurriculum.get({id : $stateParams.id});
                }]
            }
            })
            .state('courseInfo.curriculum.new', {
                parent: 'courseInfo.curriculum',
                url: '/new',
                data: {
                    authorities: [],
                },
                 views: {
                        'courseView@courseInfo':{
                           templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsCurriculum/cmsCurriculum-dialog.html',
                           controller: 'CmsCurriculumDialogController'
                        }
                     },
                resolve: {
                         entity: function () {
                             return {
                                 code: null,
                                 name: null,
                                 duration: null,
                                 description: null,
                                 status: null,
                                 id: null
                             };
                         }
                     }
               /* onEnter: ['$stateParams', '$state',  function($stateParams, $state) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsCurriculum/cmsCurriculum-dialog.html',
                        controller: 'CmsCurriculumDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    duration: null,
                                    description: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('cmsCurriculum', null, { reload: true });
                    }, function() {
                        $state.go('cmsCurriculum');
                    })
                }]*/
            })
            .state('courseInfo.curriculum.edit', {
                parent: 'courseInfo.curriculum',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                },
                 views: {
                        'courseView@courseInfo':{
                           templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsCurriculum/cmsCurriculum-dialog.html',
                           controller: 'CmsCurriculumDialogController',
                        }
                     },
                     resolve: {
                             entity: ['$stateParams','CmsCurriculum', function($stateParams,CmsCurriculum) {
                              return CmsCurriculum.get({id : $stateParams.id});
                             }]
                         }
                /*onEnter: ['$stateParams', '$state',  function($stateParams, $state) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cmsCurriculum/cmsCurriculum-dialog.html',
                        controller: 'CmsCurriculumDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CmsCurriculum', function(CmsCurriculum) {
                                return CmsCurriculum.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cmsCurriculum', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]*/
            })
            .state('courseInfo.curriculum.delete', {
                parent: 'courseInfo.curriculum',
                url: '/{id}/delete',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsCurriculum/cmsCurriculum-delete-dialog.html',
                        controller: 'CmsCurriculumDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CmsCurriculum', function(CmsCurriculum) {
                                return CmsCurriculum.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cmsCurriculum', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
