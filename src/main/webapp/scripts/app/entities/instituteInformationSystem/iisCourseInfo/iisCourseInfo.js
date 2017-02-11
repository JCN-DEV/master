'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instituteInfo.iisCourseInfo', {
                parent: 'instituteInfo',
                url: '/courseInfo-list',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.iisCourseInfo.home.title'
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/iisCourseInfo/iisCourseInfos.html',
                        controller: 'IisCourseInfoController'


                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('iisCourseInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instituteInfo.iisCourseInfo.detail', {
                parent: 'instituteInfo.iisCourseInfo',
                url: '/courseInfo-detail/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.iisCourseInfo.detail.title'
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/iisCourseInfo/iisCourseInfo-detail.html',
                        controller: 'IisCourseInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('iisCourseInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'IisCourseInfoTemp', function($stateParams, IisCourseInfoTemp) {
                        return IisCourseInfoTemp.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instituteInfo.iisCourseInfo.edit', {
                parent: 'instituteInfo.iisCourseInfo',
                url: '/{id}/edit',
                data: {
                    authorities: []
                },

                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/iisCourseInfo/iisCourseInfo-dialog.html',
                        controller: 'IisCourseInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('iisCourseInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'IisCourseInfoTemp', function($stateParams, IisCourseInfoTemp) {
                        // return IisCourseInfoTemp.get({id : $stateParams.id});
                    }]
                }
               /* onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/iisCourseInfo/iisCourseInfo-dialog.html',
                        controller: 'IisCourseInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['IisCourseInfo', function(IisCourseInfo) {
                                return IisCourseInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                            $state.go('iisCourseInfo', null, { reload: true });
                        }, function() {
                            $state.go('^');
                        })
                }]*/


            })
            .state('instituteInfo.iisCourseInfo.new', {
                parent: 'instituteInfo.iisCourseInfo',
                url: '/new',
                data: {
                    authorities: []
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/iisCourseInfo/iisCourseInfo-dialog.html',
                        controller: 'IisCourseInfoDialogController'
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            perDateEdu: null,
                            perDateBteb: null,
                            mpoEnlisted: null,
                            dateMpo: null,
                            seatNo: null,
                            shift: null,
                            id: null
                        };
                    }
                }
            })



            .state('instituteInfo.iisCourseInfo.delete', {
                parent: 'instituteInfo.iisCourseInfo',
                url: '/{id}/delete',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/iisCourseInfo/iisCourseInfo-delete-dialog.html',
                        controller: 'IisCourseInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['IisCourseInfo', function(IisCourseInfo) {
                                return IisCourseInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instituteInfo.iisCourseInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
