'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider

            .state('courseInfo.report', {
                parent: 'courseInfo',
                url: '/report',

                data: {
                    authorities: [],
                    pageTitle: 'stepApp.cmsCurriculum.detail.title'
                },
                views: {
                    'courseView@courseInfo': {
                        templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsReport/cmsReport.html',
                        controller: 'reportsController'
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


    });
