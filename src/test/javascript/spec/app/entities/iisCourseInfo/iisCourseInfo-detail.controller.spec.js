'use strict';

describe('IisCourseInfo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockIisCourseInfo, MockIisCurriculumInfo, MockCmsTrade, MockCmsSubject;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockIisCourseInfo = jasmine.createSpy('MockIisCourseInfo');
        MockIisCurriculumInfo = jasmine.createSpy('MockIisCurriculumInfo');
        MockCmsTrade = jasmine.createSpy('MockCmsTrade');
        MockCmsSubject = jasmine.createSpy('MockCmsSubject');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'IisCourseInfo': MockIisCourseInfo,
            'IisCurriculumInfo': MockIisCurriculumInfo,
            'CmsTrade': MockCmsTrade,
            'CmsSubject': MockCmsSubject
        };
        createController = function() {
            $injector.get('$controller')("IisCourseInfoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:iisCourseInfoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
