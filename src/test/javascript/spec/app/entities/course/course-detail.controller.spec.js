'use strict';

describe('Course Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockCourse, MockInstitute;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockCourse = jasmine.createSpy('MockCourse');
        MockInstitute = jasmine.createSpy('MockInstitute');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Course': MockCourse,
            'Institute': MockInstitute
        };
        createController = function() {
            $injector.get('$controller')("CourseDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:courseUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
