'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('courseInfo.cmsSyllabus', {
                parent: 'courseInfo',
                url: '/syllabus-list',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.cmsSyllabus.home.title'
                },
                views: {
                    'courseView@courseInfo': {
                        templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsSyllabus/cmsSyllabuss.html',
                        controller: 'CmsSyllabusController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cmsSyllabus');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('courseInfo.cmsSyllabus.detail', {
                parent: 'courseInfo.cmsSyllabus',
                url: '/cmsSyllabus/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.cmsSyllabus.detail.title'
                },
                views: {
                    'courseView@courseInfo': {
                        templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsSyllabus/cmsSyllabus-detail.html',
                        controller: 'CmsSyllabusDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cmsSyllabus');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CmsSyllabus', function($stateParams, CmsSyllabus) {
                        return CmsSyllabus.get({id : $stateParams.id});
                    }]
                }
            })
            .state('courseInfo.cmsSyllabus.new', {
                parent: 'courseInfo.cmsSyllabus',
                url: '/new',
                data: {
                    authorities: [],
                },
                views: {
                   'courseView@courseInfo': {
                      templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsSyllabus/cmsSyllabus-dialog.html',
                      controller: 'CmsSyllabusDialogController',
                   }
               },
                resolve: {
                   entity: function () {
                       return {
                           version: null,
                           name: null,
                           description: null,
                           file: null,
                           fileContentType: null,
                           status: null,
                           id: null
                       };
                   }
               }
               /* onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsSyllabus/cmsSyllabus-dialog.html',
                        controller: 'CmsSyllabusDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    version: null,
                                    name: null,
                                    description: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('cmsSyllabus', null, { reload: true });
                    }, function() {
                        $state.go('cmsSyllabus');
                    })
                }]*/
            })
            .state('courseInfo.cmsSyllabus.edit', {
                parent: 'courseInfo.cmsSyllabus',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                },

                views: {
                    'courseView@courseInfo':{
                       templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsSyllabus/cmsSyllabus-dialog.html',
                       controller: 'CmsSyllabusDialogController',
                    }
                 },
                 resolve: {
                         entity: ['$stateParams','CmsSyllabus', function($stateParams,CmsSyllabus) {
                          return CmsSyllabus.get({id : $stateParams.id});
                         }]
                     }
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {

                    $modal.open({
                        templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsSyllabus/cmsSyllabus-dialog.html',
                        controller: 'CmsSyllabusDialogController',
                        resolve: {
                            entity: ['CmsSyllabus', function(CmsSyllabus) {
                                return CmsSyllabus.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cmsSyllabus', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]*/
            })
            .state('cmsSyllabus.delete', {
                parent: 'cmsSyllabus',
                url: '/{id}/delete',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cmsSyllabus/cmsSyllabus-delete-dialog.html',
                        controller: 'CmsSyllabusDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CmsSyllabus', function(CmsSyllabus) {
                                return CmsSyllabus.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cmsSyllabus', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
