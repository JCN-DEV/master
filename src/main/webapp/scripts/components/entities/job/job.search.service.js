'use strict';

angular.module('stepApp')
    .factory('JobSearch', function ($resource) {
        return $resource('api/_search/jobs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('CurJobSearchLocation', function ($resource) {
        return $resource('api/_search/curJobs/location/:location', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('CurJobSearchEmployer', function ($resource) {
        return $resource('api/_search/curJobs/employer/:employer', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('CurJobSearchByCat', function ($resource) {
        return $resource('api/_search/curJobs/cat/:cat', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('CurJobSearch', function ($resource) {
        return $resource('/_search/curJobs', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('JobEmployer', function ($resource) {
        return $resource('api/jobs/employer/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('JobInfoEmployer', function ($resource) {
        return $resource('api/jobsInfo/employer/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('AvailableJobs', function ($resource) {
        return $resource('api/jobs/availableJobs', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('ArchiveJobs', function ($resource) {
        return $resource('api/jobs/archive', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('ArchiveJobsForJpadmin', function ($resource) {
        return $resource('api/jobs/archive/jpadmin', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('JobsByCategoryName', function ($resource) {
        return $resource('api/jobs/curJobs/cat/:cat', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('JobsByCategoryId', function ($resource) {
        return $resource('api/jobs/curJobs/cat/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('JobsByEmployerId', function ($resource) {
        return $resource('api/_search/curJobs/employers/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('CatsByActiveJobs', function ($resource) {
        return $resource('api/jobs/cats/activeJobs', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
