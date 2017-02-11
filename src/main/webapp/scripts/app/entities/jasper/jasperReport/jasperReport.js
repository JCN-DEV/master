'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('jasperReport', {
                parent: 'entity',
                url: '/jasperReports',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.jasperReport.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jasper/jasperReport/jasperReports.html',
                        controller: 'JasperReportController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jasperReport');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('jasperReport.detail', {
                parent: 'entity',
                url: '/jasperReport/{id}',

                data: {
                    authorities: [],
                    pageTitle: 'stepApp.jasperReport.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jasper/jasperReport/jasperReport-detail.html',
                        controller: 'JasperReportDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jasperReport');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JasperReport', function ($stateParams, JasperReport) {
                        return JasperReport.get({id: $stateParams.id});
                    }]
                }
            }).state('jasperReport.reportPreview', {
                parent: 'entity',
                url: '/jrReports/{module}',

                data: {
                    authorities: [],
                    pageTitle: 'stepApp.jasperReport.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jasper/jasperReport/jasperPreview.html',
                        controller: 'JasperReportsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jasperReport');
                        return $translate.refresh();
                    }]
                }
            })
            .state('jasperReport.new', {
                parent: 'jasperReport',
                url: '/new',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/jasper/jasperReport/jasperReport-dialog.html',
                        controller: 'JasperReportDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    path: null,
                                    module: null,
                                    role: null,
                                    status: null,
                                    createdate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function (result) {
                        $state.go('jasperReport', null, {reload: true});
                    }, function () {
                        $state.go('jasperReport');
                    })
                }]
            })
            .state('jasperReport.edit', {
                parent: 'jasperReport',
                url: '/{id}/edit',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/jasper/jasperReport/jasperReport-dialog.html',
                        controller: 'JasperReportDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['JasperReport', function (JasperReport) {
                                return JasperReport.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                        $state.go('jasperReport', null, {reload: true});
                    }, function () {
                        $state.go('^');
                    })
                }]
            });
    });
