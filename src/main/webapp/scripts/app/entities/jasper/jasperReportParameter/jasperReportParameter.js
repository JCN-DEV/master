'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('jasperReportParameter', {
                parent: 'entity',
                url: '/jasperReportParameters',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.jasperReportParameter.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jasper/jasperReportParameter/jasperReportParameters.html',
                        controller: 'JasperReportParameterController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jasperReportParameter');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('jasperReportParameter.detail', {
                parent: 'entity',
                url: '/jasperReportParameter/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.jasperReportParameter.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jasper/jasperReportParameter/jasperReportParameter-detail.html',
                        controller: 'JasperReportParameterDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jasperReportParameter');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JasperReportParameter', function ($stateParams, JasperReportParameter) {
                        return JasperReportParameter.get({id: $stateParams.id});
                    }]
                }
            })
            .state('jasperReportParameter.new', {
                parent: 'jasperReportParameter',
                url: '/new',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jasper/jasperReportParameter/jasperReportParameter-form.html',
                        controller: 'JasperReportParameterFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jasperReportParameter');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', function ($stateParams) {

                    }]
                }
            })

            //.state('jasperReportParameter.new', {
            //    parent: 'jasperReportParameter',
            //    url: '/new',
            //    data: {
            //        authorities: ['ROLE_USER'],
            //    },
            //    onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
            //        $modal.open({
            //            templateUrl: 'scripts/app/entities/jasper/jasperReportParameter/jasperReportParameter-dialog.html',
            //            controller: 'JasperReportParameterDialogController',
            //            size: 'lg',
            //            resolve: {
            //                entity: function () {
            //                    return {
            //                        name: null,
            //                        type: null,
            //                        flevel: null,
            //                        position: null,
            //                        servicename: null,
            //                        datatype: null,
            //                        combodisplayfield: null,
            //                        validationexp: null,
            //                        maxlength: null,
            //                        minlength: null,
            //                        actionname: null,
            //                        actiontype: null,
            //                        id: null
            //                    };
            //                }
            //            }
            //        }).result.then(function(result) {
            //            $state.go('jasperReportParameter', null, { reload: true });
            //        }, function() {
            //            $state.go('jasperReportParameter');
            //        })
            //    }]
            //})

            .state('jasperReportParameter.edit', {
                //parent: 'jasperReportParameter',
                //url: '/{id}/edit',
                parent: 'entity',
                url: '/{id}/edit',

                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jasper/jasperReportParameter/jasperReportParameter-form.html',
                        controller: 'JasperReportParameterFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jasperReportParameter');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JasperReportParameter', function ($stateParams, JasperReportParameter) {
                        return JasperReportParameter.get({id: $stateParams.id});
                    }]
                }
            });

        //.state('jasperReportParameter.edit', {
        //    parent: 'jasperReportParameter',
        //    url: '/{id}/edit',
        //    data: {
        //        authorities: ['ROLE_USER'],
        //    },
        //    onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
        //        $modal.open({
        //            templateUrl: 'scripts/app/entities/jasper/jasperReportParameter/jasperReportParameter-dialog.html',
        //            controller: 'JasperReportParameterDialogController',
        //            size: 'lg',
        //            resolve: {
        //                entity: ['JasperReportParameter', function(JasperReportParameter) {
        //                    return JasperReportParameter.get({id : $stateParams.id});
        //                }]
        //            }
        //        }).result.then(function(result) {
        //            //$state.go('jasperReportParameter', null, { reload: true });
        //            $state.go('jasperReport.detail', null, { reload: true });
        //        }, function() {
        //            $state.go('^');
        //        })
        //    }]
        //});
    });
