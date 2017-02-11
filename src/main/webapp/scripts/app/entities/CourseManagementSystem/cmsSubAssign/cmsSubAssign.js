'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider

            /*.state('cmsSubAssign', {
                parent: 'entity',
                url: '/cmsSubAssigns',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.cmsSubAssign.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cmsSubAssign/cmsSubAssigns.html',
                        controller: 'CmsSubAssignController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cmsSubAssign');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })*/

            .state('courseInfo.subAssign', {
                parent: 'courseInfo',
                url: '/subject-assign',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.cmsSubAssign.detail.title'
                },
                views: {
                    'courseView@courseInfo': {
                        templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsSubAssign/cmsSubAssigns.html',
                        controller: 'CmsSubAssignController'
                    }
                },
                resolve: {
                     translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                       $translatePartialLoader.addPart('cmsSubAssign');
                       $translatePartialLoader.addPart('global');
                       return $translate.refresh();
                    }],

                    entity: ['$stateParams', 'CmsSubAssign', function($stateParams, CmsSubAssign) {
                      return CmsSubAssign.get({id : $stateParams.id});
                    }]
                }
            })
            .state('courseInfo.subAssign.detail', {
                parent: 'courseInfo',
                url: '/cmsSubAssign/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.cmsSubAssign.detail.title'
                },
                views:{
                      'courseView@courseInfo': {
                          templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsSubAssign/cmsSubAssign-detail.html',
                          controller: 'CmsSubAssignDetailController'
                      }
                  },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cmsSubAssign');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CmsSubAssign', function($stateParams, CmsSubAssign) {
                        return CmsSubAssign.get({id : $stateParams.id});
                    }]
                }
            })
            .state('courseInfo.subAssign.new', {
                parent: 'courseInfo.subAssign',
                url: '/new',
                data: {
                    authorities: [],
                },
                views: {
                    'courseView@courseInfo': {
                        templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsSubAssign/cmsSubAssign-dialog.html',
                        controller: 'CmsSubAssignDialogController'
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            description: null,
                            examFee: null,
                            status: null,
                            id: null
                        };
                    }
                }
               /* onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cmsSubAssign/cmsSubAssign-dialog.html',
                        controller: 'CmsSubAssignDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    subject: null,
                                    description: null,
                                    examFee: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('cmsSubAssign', null, { reload: true });
                    }, function() {
                        $state.go('cmsSubAssign');
                    })
                }]*/
            })

            .state('courseInfo.subAssign.edit', {
                parent: 'courseInfo.subAssign',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                },
            views: {
                'courseView@courseInfo': {
                   templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsSubAssign/cmsSubAssign-dialog.html',
                   controller: 'CmsSubAssignDialogController',
                }
            },
            resolve: {
                entity: ['CmsSubAssign','$stateParams' ,function(CmsSubAssign, $stateParams) {
          /*      console.log('from router :'+ $stateParams.id);*/
                    return CmsSubAssign.get({id : $stateParams.id});
                }]
            }
               /* onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cmsSubAssign/cmsSubAssign-dialog.html',
                        controller: 'CmsSubAssignDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CmsSubAssign', function(CmsSubAssign) {
                                return CmsSubAssign.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cmsSubAssign', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]*/
            })
            .state('courseInfo.subAssign.delete', {
                parent: 'courseInfo.subAssign',
                url: '/{id}/delete',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cmsSubAssign/cmsSubAssign-delete-dialog.html',
                        controller: 'CmsSubAssignDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CmsSubAssign', function(CmsSubAssign) {
                                return CmsSubAssign.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cmsSubAssign', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
