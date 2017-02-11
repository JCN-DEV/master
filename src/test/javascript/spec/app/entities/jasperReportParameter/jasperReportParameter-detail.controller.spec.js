'use strict';

describe('JasperReportParameter Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockJasperReportParameter, MockJasperReport;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockJasperReportParameter = jasmine.createSpy('MockJasperReportParameter');
        MockJasperReport = jasmine.createSpy('MockJasperReport');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'JasperReportParameter': MockJasperReportParameter,
            'JasperReport': MockJasperReport
        };
        createController = function() {
            $injector.get('$controller')("JasperReportParameterDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:jasperReportParameterUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
