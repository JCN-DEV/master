'use strict';

describe('CourseSubject Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockCourseSubject, MockCourseTech;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockCourseSubject = jasmine.createSpy('MockCourseSubject');
        MockCourseTech = jasmine.createSpy('MockCourseTech');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'CourseSubject': MockCourseSubject,
            'CourseTech': MockCourseTech
        };
        createController = function() {
            $injector.get('$controller')("CourseSubjectDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:courseSubjectUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
