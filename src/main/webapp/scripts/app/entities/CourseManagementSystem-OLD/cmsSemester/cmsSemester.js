'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('courseInfo.cmsSemester', {
                parent: 'courseInfo',
                url: '/semester',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.cmsSemester.home.title'
                },
                views: {
                    'courseView@courseInfo': {
                        templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsSemester/cmsSemesters.html',
                        controller: 'CmsSemesterController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cmsSemester');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('courseInfo.cmsSemester.detail', {
                parent: 'courseInfo.cmsSemester',
                url: '/detail/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.cmsSemester.detail.title'
                },
                views: {
                    'courseView@courseInfo': {
                        templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsSemester/cmsSemester-detail.html',
                        controller: 'CmsSemesterDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cmsSemester');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CmsSemester', function($stateParams, CmsSemester) {
                        return CmsSemester.get({id : $stateParams.id});
                    }]
                }
            })
            .state('courseInfo.cmsSemester.new', {
                parent: 'courseInfo.cmsSemester',
                url: '/new',
                data: {
                    authorities: [],
                },
                views: {
                    'courseView@courseInfo': {
                        templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsSemester/cmsSemester-dialog.html',
                        controller: 'CmsSemesterDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cmsSemester');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity:function () {
                       return {
                           code: null,
                           name: null,
                           year: null,
                           duration: null,
                           description: null,
                           status: null,
                           id: null
                       };
                   }

                }
                /*onEnter: ['$stateParams', '$state', function($stateParams, $state) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsSemester/cmsSemester-dialog.html',
                        controller: 'CmsSemesterDialogController',

                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    year: null,
                                    duration: null,
                                    description: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('cmsSemester', null, { reload: true });
                    }, function() {
                        $state.go('cmsSemester');
                    })
                }]*/
            })
            .state('courseInfo.cmsSemester.edit', {
                parent: 'courseInfo.cmsSemester',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                },
                views: {
                'courseView@courseInfo':{
                   templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsSemester/cmsSemester-dialog.html',
                   controller: 'CmsSemesterDialogController',
                }
             },
             resolve: {
                     entity: ['$stateParams','CmsSemester', function($stateParams,CmsSemester) {
                      return CmsSemester.get({id : $stateParams.id});
                     }]
                 }
               /* onEnter: ['$stateParams', '$state', function($stateParams, $state) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsSemester/cmsSemester-dialog.html',
                        controller: 'CmsSemesterDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CmsSemester','$stateParams', function(CmsSemester) {
                                return CmsSemester.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cmsSemester', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]*/
            })
            .state('courseInfo.cmsSemester.delete', {
                parent: 'courseInfo.cmsSemester',
                url: '/{id}/delete',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', function($stateParams, $state) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsSemester/cmsSemester-delete-dialog.html',
                        controller: 'CmsSemesterDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CmsSemester', function(CmsSemester) {
                                return CmsSemester.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cmsSemester', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

